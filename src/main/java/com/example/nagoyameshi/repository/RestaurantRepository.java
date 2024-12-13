package com.example.nagoyameshi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Restaurant;

public interface RestaurantRepository extends  JpaRepository<Restaurant, Integer>{
	public Page<Restaurant> findByNameLike(String keyword, Pageable pageable);
	
    public Page<Restaurant> findByNameLikeOrAddressLikeOrderByCreateDateDesc(String nameKeyword, String addressKeyword, Pageable pageable);  
    public Page<Restaurant> findByNameLikeOrAddressLikeOrderByPriceAsc(String nameKeyword, String addressKeyword, Pageable pageable);  
    public Page<Restaurant> findByCategoryIdOrderByCreateDateDesc(Integer category, Pageable pageable);
    public Page<Restaurant> findByCategoryIdOrderByPriceAsc(Integer category, Pageable pageable);
    public Page<Restaurant> findByPriceLessThanEqualOrderByCreateDateDesc(Integer price, Pageable pageable);
    public Page<Restaurant> findByPriceLessThanEqualOrderByPriceAsc(Integer price, Pageable pageable); 
    public Page<Restaurant> findAllByOrderByCreateDateDesc(Pageable pageable);
    public Page<Restaurant> findAllByOrderByPriceAsc(Pageable pageable);  
    public Page<Restaurant> findByCategoryIdOrderById(Integer id ,Pageable pageable);  

    public List <Restaurant> findTop5ByOrderByCreateDateDesc();
    
}
