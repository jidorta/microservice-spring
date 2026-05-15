package com.ewolff.microservice.order.repository;

import java.util.List;
import com.ewolff.microservice.order.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

	List<Customer> findByName(String name);

}
