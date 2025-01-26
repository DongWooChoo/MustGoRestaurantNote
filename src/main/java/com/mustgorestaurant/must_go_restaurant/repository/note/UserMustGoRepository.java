package com.mustgorestaurant.must_go_restaurant.repository.note;

import com.mustgorestaurant.must_go_restaurant.entity.note.UserMustGo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMustGoRepository extends JpaRepository<UserMustGo, Long> {
    boolean existsByRestaurantNameAndAddress(String restaurantName, String address);
}
