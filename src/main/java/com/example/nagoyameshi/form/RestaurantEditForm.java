package com.example.nagoyameshi.form;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class RestaurantEditForm {
	
    @NotNull
    private Integer id;   
    
    private Integer categoryId;    
    
    @NotBlank(message = "店舗名を入力してください。")
    private String name;
        
    private MultipartFile imageFile;
    
    @NotBlank(message = "説明を入力してください。")
    private String description;   
    
    @NotNull(message = "料金を入力してください。")
    @Min(value = 1, message = "宿泊料金は1円以上に設定してください。")
    private Integer price; 
    
    @NotNull(message = "定員を入力してください。")
    @Min(value = 1, message = "定員は1人以上に設定してください。")
    private Integer capacity;       
    
    @NotBlank(message = "郵便番号を入力してください。")
    private String postalCode;
    
    @NotBlank(message = "住所を入力してください。")
    private String address;
    
    @NotBlank(message = "電話番号を入力してください。")
    private String phoneNumber;


}
