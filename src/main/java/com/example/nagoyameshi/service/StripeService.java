package com.example.nagoyameshi.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class StripeService {
	
    @Value("${stripe.api-key}")
    private String stripeApiKey;
    
    private final UserService userService;
    
    public StripeService(UserService userService) {
        this.userService = userService;
    }    
	
    // セッションを作成し、Stripeに必要な情報を返す
    public String createStripeSession(Integer userId,String email,HttpServletRequest httpServletRequest) {
        Stripe.apiKey = stripeApiKey;
        String requestUrl = new String(httpServletRequest.getRequestURL());

        // サブスクリプション価格ID
        final String subscriptionPriceId = "price_1QVkYmAcYK9h7ZJRlI8wHpWX";

        // カスタマーのメタデータを設定
        Map<String, String> metadata = new HashMap<>();
        metadata.put("userId", userId.toString());
        metadata.put("email", email.toString());        

        // カスタマーを作成し、メタデータを付与
        CustomerCreateParams customerParams = CustomerCreateParams.builder()
            .putAllMetadata(metadata)
            .build();
        
        try {
        	Customer customer = Customer.create(customerParams);
        
	        SessionCreateParams params =
	            SessionCreateParams.builder()
	                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
	                .addLineItem(
	                    SessionCreateParams.LineItem.builder()
	                        .setPrice(subscriptionPriceId) // サブスクリプション価格IDを設定
	                        .setQuantity(1L)
	                        .build())
	                .setMode(SessionCreateParams.Mode.SUBSCRIPTION) // サブスクリプションモードに設定
	                .setSuccessUrl(requestUrl.replaceAll("/paid", "/paid/completed/") + userId)
	                .setCancelUrl(requestUrl)
	                .setCustomer(customer.getId())  // 作成した顧客を使用
	                .build();
	        
	
	            Session session = Session.create(params);
	            return session.getId();
	            
        } catch (StripeException e) {
        	e.printStackTrace();
        	return "";
        	}
        }

    // セッションから会員情報を取得し、UserServiceクラスを介してデータベースに登録する  
    public void processSessionCompleted(Event event) {
        Optional<StripeObject> optionalStripeObject = event.getDataObjectDeserializer().getObject();
        optionalStripeObject.ifPresentOrElse(stripeObject -> {
            Session session = (Session)stripeObject;
            String customerId = session.getCustomer();

            try {
            	 // カスタマーIDからカスタマーを取得
            	Customer customer = Customer.retrieve(customerId);
            	
            	// カスタマーのメタデータを取得
            	Map<String, String> metadata = customer.getMetadata();
            	
            	// メタデータを使用して処理を行う
                Integer userId = Integer.parseInt(metadata.get("userId"));
                String email = metadata.get("email");

                userService.updatePaid(userId);
                
            } catch (StripeException e) {
                e.printStackTrace();
            }
            System.out.println("登録処理が成功しました。");
            System.out.println("Stripe API Version: " + event.getApiVersion());
            System.out.println("stripe-java Version: " + Stripe.VERSION);
        },
        () -> {
            System.out.println("登録処理が失敗しました。");
            System.out.println("Stripe API Version: " + event.getApiVersion());
            System.out.println("stripe-java Version: " + Stripe.VERSION);
        });
    }
    
}

