package com.example.nagoyameshi.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.StripeService;
import com.example.nagoyameshi.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/paid")
public class PaidController {
	
	 private final StripeService stripeService;
	 private final UserService userService; 
	 private final UserRepository userRepository;    
	 
	    public PaidController(StripeService stripeService,
	    									UserService userService,
	    									UserRepository userRepository) {
	    	this.stripeService = stripeService;
	    	this.userService = userService;
	    	this.userRepository = userRepository;
	    }
	    
    // 有料会員情報を開く
	@GetMapping
	public String index( HttpServletRequest httpServletRequest,
										@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
										Model model) {
		
		User user = userDetailsImpl.getUser(); 
		String sessionId = stripeService.createStripeSession(user.getId(),user.getEmail(),httpServletRequest);
		
		model.addAttribute("sessionId", sessionId);
		
		return "paid/index";
	}
	
	// サブスクリプション支払いの完了処理
	@GetMapping("/completed/{id}")
	@Transactional
	public String handleSuccess(HttpServletRequest httpServletRequest,
													@PathVariable(name = "id") Integer userId, 
													RedirectAttributes redirectAttributes) {
		
		User user = userRepository.getReferenceById(userId);
		userService.updateUserRole(user.getEmail() , httpServletRequest);
        redirectAttributes.addFlashAttribute("successMessage", "会員情報を更新しました。");
        
        return "redirect:/";
	}

}
