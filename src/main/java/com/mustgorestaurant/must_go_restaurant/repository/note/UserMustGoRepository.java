package com.mustgorestaurant.must_go_restaurant.repository.note;

import com.mustgorestaurant.must_go_restaurant.entity.note.UserMustGo;
import com.mustgorestaurant.must_go_restaurant.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface UserMustGoRepository extends JpaRepository<UserMustGo, Long> {

    // 식당명과 주소 값을 기준으로 중복된 값이 존재하는지 검사
    boolean existsByRestaurantNameAndAddress(String restaurantName, String address);

    // 사용자가 작성한 식당 정보 리스트 조회
    List<UserMustGo> findByUserInfo(UserInfo userInfo);

    // 사용자가 작성한 식당 정보 수정
    @Modifying
    @Transactional
    @Query("UPDATE UserMustGo u SET u.restaurantName = :restaurantName, u.restaurantInfo = :restaurantInfo, u.address = :address WHERE u.postIdx = :postIdx AND u.userInfo = :userInfo")
    int updateNoteByPostIdx(long postIdx, String restaurantName, String restaurantInfo, String address, UserInfo userInfo);

    // 사용자가 작성한 식당 정보 삭제 (식당명, 주소, 사용자 ID가 일치하는 값을 기준으로 삭제 진행)
    @Modifying
    @Transactional
    @Query("DELETE FROM UserMustGo u WHERE u.postIdx = :postIdx AND u.userInfo = :userInfo")
    int deleteAndReturnByPostIdx(long postIdx, UserInfo userInfo);

}
