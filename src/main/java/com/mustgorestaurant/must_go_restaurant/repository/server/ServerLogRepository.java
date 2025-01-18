package com.mustgorestaurant.must_go_restaurant.repository.server;

import com.mustgorestaurant.must_go_restaurant.entity.server.ServerLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerLogRepository extends JpaRepository<ServerLog, Long> {
}
