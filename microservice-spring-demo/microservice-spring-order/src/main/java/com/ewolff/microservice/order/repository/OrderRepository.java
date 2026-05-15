package com.ewolff.microservice.order.repository;

import java.time.LocalDateTime;
import java.util.Date;

import com.ewolff.microservice.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order,Long>{
	@Query("SELECT max(o.updated) FROM Order o")
    LocalDateTime lastUpdate();

}
