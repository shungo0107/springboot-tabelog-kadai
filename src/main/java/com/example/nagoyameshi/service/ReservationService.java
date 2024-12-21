package com.example.nagoyameshi.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationRegisterForm;
import com.example.nagoyameshi.repository.ReservationRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.repository.UserRepository;

@Service
public class ReservationService {
	
    private final ReservationRepository reservationRepository;  
    private final RestaurantRepository restaurantRepository;  
    private final UserRepository userRepository;  
    
    public ReservationService(ReservationRepository reservationRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;  
        this.restaurantRepository = restaurantRepository;  
        this.userRepository = userRepository;  
    }    
    
    @Transactional
    public void create(ReservationRegisterForm reservationRegisterForm) { 
        Reservation reservation = new Reservation();
        Restaurant restaurant = restaurantRepository.getReferenceById(reservationRegisterForm.getRestaurantId());
        User user = userRepository.getReferenceById(reservationRegisterForm.getUserId());
        LocalDate reserveDate = LocalDate.parse(reservationRegisterForm.getReserveDate());        
                
        reservation.setRestaurant(restaurant);
        reservation.setUser(user);
        reservation.setReserveDate(reserveDate);
        reservation.setNumberOfPeople(reservationRegisterForm.getNumberOfPeople());
        reservation.setAmount(reservationRegisterForm.getAmount());
        
        reservationRepository.save(reservation);
    } 
    
    @Transactional
    public void delete(Integer reservationId) { 
        reservationRepository.deleteById(reservationId);
    } 
    
    // 人数が定員以下かどうかをチェックする
    public boolean isWithinCapacity(Integer numberOfPeople, Integer capacity) {
        return numberOfPeople <= capacity;
    }
    
    // 料金を計算する
    public Integer calculateAmount(Integer numberOfPeople,  Integer price) {
        return price * numberOfPeople;
    }  
}