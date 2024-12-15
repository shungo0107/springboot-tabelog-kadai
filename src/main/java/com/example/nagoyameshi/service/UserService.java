package com.example.nagoyameshi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Role;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ResetInputForm;
import com.example.nagoyameshi.form.SignupForm;
import com.example.nagoyameshi.form.UserEditForm;
import com.example.nagoyameshi.repository.RoleRepository;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.security.UserDetailsServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class UserService {
     private final UserRepository userRepository;
     private final RoleRepository roleRepository;
     private final PasswordEncoder passwordEncoder;
     
     public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
         this.userRepository = userRepository;
         this.roleRepository = roleRepository;        
         this.passwordEncoder = passwordEncoder;
     }    
     
     @Autowired
     private UserDetailsServiceImpl  userDetailsServiceImpl ;
     
     // 会員情報を新規登録する
     @Transactional
     public User create(SignupForm signupForm) {
         User user = new User();
         Role role = roleRepository.findByName("ROLE_GENERAL");
         
         user.setName(signupForm.getName());
         user.setFurigana(signupForm.getFurigana());
         user.setEmail(signupForm.getEmail());
         user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
         user.setRole(role);
         user.setEnabled(false);        
         
         return userRepository.save(user);
     } 
     
     // 会員情報を更新する
     @Transactional
     public void update(UserEditForm userEditForm) {
         User user = userRepository.getReferenceById(userEditForm.getId());

         user.setName(userEditForm.getName());
         user.setFurigana(userEditForm.getFurigana());
         user.setEmail(userEditForm.getEmail());      
         
         userRepository.save(user);
       
     }   

     // 有料会員登録を行う
     @Transactional
     public void updatePaid(Integer userId) {
    	 
         System.out.println("=================updatePaid開始=================");
         User user = userRepository.getReferenceById(userId);
         Role role = roleRepository.findByName("ROLE_PAID");
         user.setRole(role); 
         userRepository.save(user);
         System.out.println("=================updatePaid終了=================");

     }   
     
     // クレジットカード情報を登録する
     @Transactional
     public void createCredit(Integer userId,String customerId) {
    	 
         System.out.println("=================createCredit開始=================");
         System.out.println("paymentMethodId：" + customerId);
         User user = userRepository.getReferenceById(userId);
         user.setStripeCustomerId(customerId); 
         userRepository.save(user);
         System.out.println("=================createCredit終了=================");
  
     }
     
     
     // パスワードを再設定する
     @Transactional
     public void updatePassword(Integer id ,ResetInputForm resetInputForm) {
         User user = userRepository.getReferenceById(id);
         user.setPassword(passwordEncoder.encode(resetInputForm.getPassword()));
         userRepository.save(user);
     } 
     
     // メールアドレスが登録済みかどうかをチェックする
     public boolean isEmailRegistered(String email) {
         User user = userRepository.findByEmail(email);  
         return user != null;
     }  
     
     // パスワードとパスワード（確認用）の入力値が一致するかどうかをチェックする
     public boolean isSamePassword(String password, String passwordConfirmation) {
         return password.equals(passwordConfirmation);
     }   
     
     // [メール認証]　ユーザーを有効にする
     @Transactional
     public void enableUser(User user) {
         user.setEnabled(true); 
         userRepository.save(user);
     }   
     
     // メールアドレスが変更されたかどうかをチェックする
     public boolean isEmailChanged(UserEditForm userEditForm) {
         User currentUser = userRepository.getReferenceById(userEditForm.getId());
         return !userEditForm.getEmail().equals(currentUser.getEmail());      
     }  
     
     // 認証情報を更新する
     public void updateUserRole(String email,HttpServletRequest httpServletRequest) {
    	 
    	 // 現在のHTTPセッションを取得する。
    	 // false → セッションが存在しない場合には新たにセッションを作成しない。
    	 HttpSession session = httpServletRequest.getSession(false);
    	 
    	 // セッションが存在する場合、そのセッションを無効にする。
    	 if (session != null) {
    		    session.invalidate();
    		}
    	 
    	 // Spring Securityのセキュリティコンテキストをクリアする。現在のスレッドに関連付けられた認証情報がリセットされる。
    	 SecurityContextHolder.clearContext();
    	 
         UserDetails userDetails = userDetailsServiceImpl .loadUserByUsername(email);
	         System.out.println("○○○○○○○○○○");
	         System.out.println("Username：" + userDetails.getUsername());
	         System.out.println("Authorities：" + userDetails.getAuthorities());         
	         System.out.println("○○○○○○○○○○");
	     
	     // 新しい認証情報を作成する。
         Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
         
         // 新しく作成した認証情報を現在のセキュリティコンテキストに設定する。これにより、認証情報が更新される。
	     SecurityContextHolder.getContext().setAuthentication(auth);
	         System.out.println("○○○○○○○○○○");
	         System.out.println("SecurityContextHolder：" + SecurityContextHolder.getContext().getAuthentication());
	         System.out.println("○○○○○○○○○○");
	     
	     // 新しいセッションを作成する。
	     // true→セッションが存在しない場合には新たに作成する。
	     HttpSession newSession = httpServletRequest.getSession(true);
	     
	     // 新しいセッションに、更新されたセキュリティコンテキストを設定する。これにより、新しいセッションに最新の認証情報が保持される。
	     newSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
     }     

}
