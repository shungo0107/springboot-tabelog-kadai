package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationInputForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.RestaurantService;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;        
    private final FavoriteRepository favoriteRepository;
    private final CategoryRepository categoryRepository;
    private final RestaurantService restaurantService;
    
    public RestaurantController(RestaurantRepository restaurantRepository,
    													ReviewRepository reviewRepository,
    													FavoriteRepository favoriteRepository,
    													CategoryRepository categoryRepository,
    													RestaurantService restaurantService) {
        this.restaurantRepository = restaurantRepository; 
        this.favoriteRepository = favoriteRepository;  
        this.reviewRepository = reviewRepository;
        this.categoryRepository = categoryRepository;
        this.restaurantService = restaurantService;
    }     
  
    @GetMapping
    public String index(@RequestParam(name = "keyword", required = false) String keyword,
                        @RequestParam(name = "category", required = false) Integer category,
                        @RequestParam(name = "price", required = false) Integer price,
                        @RequestParam(name = "order", required = false, defaultValue = "id-asc") String order,
                        @PageableDefault(page = 0, size = 10) Pageable defaultPageable,
                        Model model) 
    {

    	String[] orderList = order.split("-");
    	String orderKey = orderList[0];
    	String orderDirecition = orderList[1];    	

    	System.out.println("keyword：" + keyword);
    	System.out.println("category：" + category);
    	System.out.println("price：" + price);
    	System.out.println("orderKey：" + orderKey);
    	System.out.println("orderDirecition：" + orderDirecition);
    	
    	// 入力されたdirectionに基づいてSort.Directionを決定
        Sort.Direction sortDirection = orderDirecition.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

    	System.out.println("sortDirection：" + sortDirection);
        
        // Sortオブジェクトを作成
        Sort sort = Sort.by(sortDirection , orderKey);
        
        // PageRequestを使用してPageableを作成
        Pageable pageable = PageRequest.of(defaultPageable.getPageNumber(), defaultPageable.getPageSize(), sort);
        
    	System.out.println("pageable：" + pageable);
        
        Page<Restaurant> restaurantPage =  restaurantService.findRestaurantsWithDynamicCriteria(keyword, keyword, category, price, pageable);
        List<Category> categoryList = categoryRepository.findAll();      

        model.addAttribute("restaurantPage", restaurantPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);
        model.addAttribute("price", price);
        model.addAttribute("order", order);
        model.addAttribute("categoryList", categoryList);
        
        return "restaurants/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer restaurantId, 
    									@PageableDefault(page = 0, size = 6, direction = Direction.ASC) Pageable pageable,
    									@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, 
    									Model model) {
    	
        Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
        Page<Review> review = reviewRepository.findByRestaurantIdOrderByCreateDate(restaurantId,pageable);
        
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("reservationInputForm", new ReservationInputForm());
        model.addAttribute("review", review);
        
        if (userDetailsImpl != null) {
        	User user = userDetailsImpl.getUser();
        	boolean hasUserReviewed = !reviewRepository.findByRestaurantIdAndUserId(restaurantId, user.getId()).isEmpty();
            boolean hasUserFavorite  =  !favoriteRepository.findByRestaurantIdAndUserId(restaurantId, user.getId()).isEmpty();
        	
		    model.addAttribute("hasUserReviewed", hasUserReviewed);
		    model.addAttribute("hasUserFavorite", hasUserFavorite);
		    model.addAttribute("user", user);
        }
        
        return "restaurants/show";
    } 
}