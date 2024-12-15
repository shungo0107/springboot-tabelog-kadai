package com.example.nagoyameshi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.UserService;
import com.stripe.Stripe;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethod;

@RestController
public class StripeController {

    @Value("${stripe.api-key}")
    private String stripeApiKey;
    
    private final UserService userService;
    
    public StripeController(UserService userService) {
    	this.userService = userService;
    }

    @PostMapping("/create-stripe-customer")
    public String createStripeCustomer(@RequestBody Map<String, String> data,
    															@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    															RedirectAttributes redirectAttributes) {
        // StripeAPIキーの設定
        Stripe.apiKey = stripeApiKey;

        try {
            // ペイメントメソッドIDを取得
            String paymentMethodId = data.get("paymentMethodId");
            
            // カード名義人の名前を取得
            String cardholderName = data.get("cardholderName");
            
            // 顧客を作成
            Map<String, Object> customerParams = new HashMap<>();
            customerParams.put("payment_method", paymentMethodId);
            Customer customer = Customer.create(customerParams);

            // カスタマーIDを取得
            String customerId = customer.getId();

        	System.out.println("□□□□□□□□□□□□□");
            System.out.println("customer：" + customer.toJson());
        	System.out.println("□□□□□□□□□□□□□");
            
        	// ペイメントメソッドを顧客にアタッチ
            Map<String, Object> attachParams = new HashMap<>();
            attachParams.put("customer", customerId);
        	
            // 支払方法を取得して、billing_detailsを更新
            PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentMethodId);
            Map<String, Object> billingDetails = new HashMap<>();
            billingDetails.put("name", cardholderName);
            
            Map<String, Object> updateParams = new HashMap<>();
            updateParams.put("billing_details", billingDetails);
            
            paymentMethod = paymentMethod.update(updateParams);
            paymentMethod.attach(attachParams);

        	System.out.println("■■■■■■■■■■■■■");
          	System.out.println("paymentMethod：" + paymentMethod);
            System.out.println("■■■■■■■■■■■■■");
            
            userService.createCredit(userDetailsImpl.getUser().getId(),customerId);
        	
            return customer.toJson();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}