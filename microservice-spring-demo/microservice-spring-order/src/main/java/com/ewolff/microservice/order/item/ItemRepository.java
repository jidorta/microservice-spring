package com.ewolff.microservice.order.item;

import java.util.List;

import com.ewolff.microservice.order.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item,Long> {

	List<Item> findByName(String name);

	List<Item> findByNameContaining(String name);

	@Query("SELECT i.price FROM Item i WHERE i.itemId=?1")
	double findPrinceByItemId(long itemId);

}
