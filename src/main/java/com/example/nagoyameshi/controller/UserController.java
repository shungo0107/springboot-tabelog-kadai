package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.UserEditForm;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.UserService;
import com.stripe.Stripe;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentMethodListParams;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;    
    private final UserService userService; 
    
    public UserController(UserRepository userRepository, 
    										UserService userService) {
        this.userRepository = userRepository; 
         this.userService = userService;
    }

	   @Value("${stripe.api-key}")
	    String stripeApiKey;

    
    @GetMapping
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, 
    									Model model) {         
        User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());  
        
        model.addAttribute("user", user);
        
        return "user/index";
    }
    
    @GetMapping("/edit")
    public String edit(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, 
    								Model model) {        
        User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());  
        UserEditForm userEditForm = new UserEditForm(user.getId(), user.getName(), user.getFurigana(), user.getEmail());
        
        model.addAttribute("userEditForm", userEditForm);
        
        return "user/edit";
    }  
    
    @PostMapping("/update")
    public String update(@ModelAttribute @Validated UserEditForm userEditForm, 
    									@RequestParam(name = "paidInfoChangeFlg", required = false, defaultValue="off") String checkFlg,
										HttpServletRequest httpServletRequest,
    									BindingResult bindingResult, 
    									RedirectAttributes redirectAttributes,
    									Model model) {

        // メールアドレスが変更されており、かつ登録済みであれば、BindingResultオブジェクトにエラー内容を追加する
        if (userService.isEmailChanged(userEditForm) && userService.isEmailRegistered(userEditForm.getEmail())) {
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "すでに登録済みのメールアドレスです。");
            bindingResult.addError(fieldError);                       
        }
        
        if (bindingResult.hasErrors()) {
            return "user/edit";
        }
        
        userService.update(userEditForm);
        
        // 有料会員情報の更新有無で切り替える
        if(checkFlg.equals("on")){
        	        	
        	User user = userRepository.getReferenceById(userEditForm.getId());
        	
        	// StripeAPIキーの設定
            Stripe.apiKey = stripeApiKey;
        	
        	try {
                // クレジットカード情報を取得するための顧客ID
                String customerId = user.getStripeCustomerId();  // 実際の顧客IDを使用

                PaymentMethodListParams params = PaymentMethodListParams.builder()
                    .setCustomer(customerId)
                    .setType(PaymentMethodListParams.Type.CARD)
                    .build();

                List<PaymentMethod> paymentMethods = PaymentMethod.list(params).getData();

                // カード情報を取り出し
                for (PaymentMethod paymentMethod : paymentMethods) {
                    PaymentMethod.Card card = paymentMethod.getCard();
                    if (card != null) {
                        String maskedCardNumber = "**** **** **** " +card.getLast4();
                        String brand = card.getBrand(); 				// カードブランド。
                        Long expMonth = card.getExpMonth(); 	// 有効期限の月
                        Long expYear = card.getExpYear(); 			// 有効期限の年
                        String monthYear = String.format("%02d月 ／ %d年", expMonth, expYear);
                        
                        model.addAttribute("cardNumber", maskedCardNumber);
                        model.addAttribute("brand", brand);
                        model.addAttribute("monthYear", monthYear);
                        
                        // 名義人情報の取得
                        PaymentMethod.BillingDetails billingDetails = paymentMethod.getBillingDetails();
                        
                        if (billingDetails != null) {
                            String cardholderName = billingDetails.getName();
                            model.addAttribute("cardholderName", cardholderName);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        	model.addAttribute("user", user);
        	return "paid/edit";
        } else {
        	
            // 認証情報を最新化する
            User user = userRepository.getReferenceById(userEditForm.getId());
    		userService.updateUserRole(user.getEmail() , httpServletRequest);
    		
        	redirectAttributes.addFlashAttribute("successMessage", "会員情報を編集しました。");
        	return "redirect:/user";        	
        }
    }  
    
    // 有料会員を解約する（クレジットカード情報が未登録の場合）
    // ビューの指定が必要ないため、空のJSONファイルをリターンする。
    @GetMapping("/delete")
    @ResponseBody
    public ResponseEntity<Void> deletePaid(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    											RedirectAttributes redirectAttributes,
    											HttpServletRequest httpServletRequest) {

    	userService.cancelCredit(userDetailsImpl.getUser().getId());
    	
        // 認証情報を最新化する
        User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		userService.updateUserRole(user.getEmail() , httpServletRequest);

    	return ResponseEntity.ok().build();
    }
}