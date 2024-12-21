package com.example.nagoyameshi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.UserService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethod;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class StripeController {

    @Value("${stripe.api-key}")
    private String stripeApiKey;
    
    private final UserService userService;
    private final UserRepository userRepository;
    
    public StripeController(UserService userService,
    										UserRepository userRepository) {
    	this.userService = userService;
    	this.userRepository = userRepository;
    }

    // クレジットカード情報を登録する
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
            customerParams.put("name", userDetailsImpl.getUser().getName());
            
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
    
    @PostMapping("/update-payment-method")
    public String updateStripeCustomer(@RequestBody Map<String, String> data,
    															@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    															RedirectAttributes redirectAttributes) {
        // StripeAPIキーの設定
        Stripe.apiKey = stripeApiKey;

        try {
            // クライアントから送信された paymentMethodId と customerId を取得
            String paymentMethodId = data.get("paymentMethodId");
            String cardholderName = data.get("cardholderName");
            String customerId = data.get("customerId");
            
            // 新しいペイメントメソッドを取得し、顧客に関連付ける
            PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentMethodId);
            paymentMethod.attach(Map.of("customer", customerId));

            // ペイメントメソッドのbillingDetailsを更新
            Map<String, Object> paymentMethodParams = new HashMap<>();
            Map<String, Object> billingDetails = new HashMap<>();
            billingDetails.put("name", cardholderName);
            
            paymentMethodParams.put("billing_details", billingDetails);
            paymentMethod = paymentMethod.update(paymentMethodParams);

            // 顧客のデフォルト支払い方法を新しいペイメントメソッドに更新
            Customer customer = Customer.retrieve(customerId);
            Map<String, Object> params = new HashMap<>();
            params.put("invoice_settings", Map.of("default_payment_method", paymentMethodId));

            customer.update(params);
            
            return "Customer updated successfully.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
    
    @PostMapping("/delete-customer")
    public String deleteCustomer(@RequestBody Map<String, String> data,
    													@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
    													HttpServletRequest httpServletRequest) {
        // StripeAPIキーの設定
        Stripe.apiKey = stripeApiKey;
        
        try {
            // クライアントから送信された customerId を取得
            String customerId = data.get("customerId");
            
            System.out.println("１★★★★★★★★★★★★");
            
            if (customerId == null || customerId.isEmpty()) {
                return "Error: Customer ID is required";
            }
            
            System.out.println("２★★★★★★★★★★★★");

            // 顧客を削除
            Customer customer = Customer.retrieve(customerId);
            customer.delete();
            
            System.out.println("３★★★★★★★★★★★★");
            
            // DB側のカスタマーIDを更新する（null）
            Integer userId = userDetailsImpl.getUser().getId();
            userService.cancelCredit(userId);
            
            // 認証情報を最新化する
            User user = userRepository.getReferenceById(userId);
    		userService.updateUserRole(user.getEmail() , httpServletRequest);

            return "Customer deleted successfully.";

        } catch (StripeException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}