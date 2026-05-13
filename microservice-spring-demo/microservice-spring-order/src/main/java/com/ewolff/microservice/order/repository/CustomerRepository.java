package com.ewolff.microservice.order.repository;

import java.util.List;

import com.ewolff.microservice.order.customer.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>, CrudRepository<Customer, Long> {

	List<Customer> findByName(@Param("name") String name);

}
