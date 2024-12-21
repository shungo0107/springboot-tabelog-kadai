package com.example.nagoyameshi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.form.RestaurantEditForm;
import com.example.nagoyameshi.form.RestaurantRegisterForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;    
    private final CategoryRepository categoryRepository;
    
    public RestaurantService(RestaurantRepository restaurantRepository,
    												CategoryRepository categoryRepository) {
        this.restaurantRepository = restaurantRepository;
        this.categoryRepository = categoryRepository;
    } 
    
    // 店舗を検索する
    public Page<Restaurant> findRestaurantsWithDynamicCriteria(String name, 
    																											String address, 
    																											Integer category, 
    																											Integer price,
    																											Pageable pageable) {
    	
        Specification<Restaurant> spec = Specification.where(RestaurantSpecifications.hasName(name)
        																				.or(RestaurantSpecifications.hasAddress(address)))
        																				.and(RestaurantSpecifications.hasCategory(category))
                                                										.and(RestaurantSpecifications.priceLessThanOrEqualTo(price));

        return restaurantRepository.findAll(spec, pageable);
    }
    
    // 店舗を作成する
    @Transactional
    public void create(RestaurantRegisterForm restaurantRegisterForm) {
        Restaurant restaurant = new Restaurant();
        Category category = categoryRepository.getReferenceById(restaurantRegisterForm.getCategoryId());
        MultipartFile imageFile = restaurantRegisterForm.getImageFile();
        
        
        if (!imageFile.isEmpty()) {
            String imageName = imageFile.getOriginalFilename(); 
            String hashedImageName = generateNewFileName(imageName);
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
            copyImageFile(imageFile, filePath);
            restaurant.setImageName(hashedImageName);
        } else {
        	restaurant.setImageName("noimage.png");
        }
        
        restaurant.setCategory(category);                
        restaurant.setName(restaurantRegisterForm.getName());                
        restaurant.setDescription(restaurantRegisterForm.getDescription());
        restaurant.setPrice(restaurantRegisterForm.getPrice());
        restaurant.setCapacity(restaurantRegisterForm.getCapacity());
        restaurant.setPostalCode(restaurantRegisterForm.getPostalCode());
        restaurant.setAddress(restaurantRegisterForm.getAddress());
        restaurant.setPhoneNumber(restaurantRegisterForm.getPhoneNumber());
                    
        restaurantRepository.save(restaurant);
    }  
    
    // 店舗情報を更新する
    @Transactional
    public void update(RestaurantEditForm restaurantEditForm) {
        Restaurant restaurant = restaurantRepository.getReferenceById(restaurantEditForm.getId());
        Category category = categoryRepository.getReferenceById(restaurantEditForm.getCategoryId());
        MultipartFile imageFile = restaurantEditForm.getImageFile();
        
        if (!imageFile.isEmpty()) {
            String imageName = imageFile.getOriginalFilename(); 
            String hashedImageName = generateNewFileName(imageName);
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
            copyImageFile(imageFile, filePath);
            restaurant.setImageName(hashedImageName);
        } else {
        	restaurant.setImageName("noimage.png");
        }

        restaurant.setCategory(category);                
        restaurant.setName(restaurantEditForm.getName());                
        restaurant.setDescription(restaurantEditForm.getDescription());
        restaurant.setPrice(restaurantEditForm.getPrice());
        restaurant.setCapacity(restaurantEditForm.getCapacity());
        restaurant.setPostalCode(restaurantEditForm.getPostalCode());
        restaurant.setAddress(restaurantEditForm.getAddress());
        restaurant.setPhoneNumber(restaurantEditForm.getPhoneNumber());
                    
        restaurantRepository.save(restaurant);
    }  
    
    
    // UUIDを使って生成したファイル名を返す
    public String generateNewFileName(String fileName) {
        String[] fileNames = fileName.split("\\.");                
        for (int i = 0; i < fileNames.length - 1; i++) {
            fileNames[i] = UUID.randomUUID().toString();            
        }
        String hashedFileName = String.join(".", fileNames);
        return hashedFileName;
    }     
    
    // 画像ファイルを指定したファイルにコピーする
    public void copyImageFile(MultipartFile imageFile, Path filePath) {           
        try {
            Files.copy(imageFile.getInputStream(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }          
    } 
}

