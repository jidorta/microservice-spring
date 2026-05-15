package com.ewolff.microservice.order;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ewolff.microservice.order.repository.CustomerRepository;
import com.ewolff.microservice.order.bootstrap.CustomerTestDataGenerator;
import com.ewolff.microservice.order.item.ItemRepository;
import com.ewolff.microservice.order.bootstrap.ItemTestDataGenerator;
import com.ewolff.microservice.order.entity.Address;
import com.ewolff.microservice.order.entity.Order;
import com.ewolff.microservice.order.repository.OrderRepository;

@Component
@Profile("test")
@DependsOn({ "itemTestDataGenerator", "customerTestDataGenerator" })
public class OrderTestDataGenerator {

	private final OrderRepository orderRepository;
	private ItemRepository itemRepository;
	private CustomerRepository customerRepository;
	private ItemTestDataGenerator itemTestDataGenerator;
	private CustomerTestDataGenerator customerTestDataGenerator;

	public OrderTestDataGenerator(OrderRepository orderRepository, ItemRepository itemRepository,
			CustomerRepository customerRepository, CustomerTestDataGenerator customerTestDataGenerator,
			ItemTestDataGenerator itemTestDataGenerator) {
		this.orderRepository = orderRepository;
		this.itemRepository = itemRepository;
		this.customerRepository = customerRepository;
		this.itemTestDataGenerator = itemTestDataGenerator;
		this.customerTestDataGenerator = customerTestDataGenerator;
	}

	@PostConstruct
	public void generateTestData() {
		itemTestDataGenerator.generateTestData();
		customerTestDataGenerator.generateTestData();
		Order order = new Order(customerRepository.findAll(Sort.unsorted()).iterator().next(), 1);
		order.setShippingAddress(new Address("Ohlauer Str. 43", "10999", "Berlin"));
		order.setBillingAddress(new Address("Krischerstr. 100", "40789", "Monheim am Rhein"));
		order.setDeliveryService("Hermes");
		order.addLine(42, itemRepository.findAll(Sort.unsorted()).iterator().next());
		order = orderRepository.save(order);
		orderRepository.save(order);
	}

}
