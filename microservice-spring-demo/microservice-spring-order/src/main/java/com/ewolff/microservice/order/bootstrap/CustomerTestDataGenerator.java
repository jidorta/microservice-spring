package com.ewolff.microservice.order.bootstrap;

import com.ewolff.microservice.order.entity.Customer;
import com.ewolff.microservice.order.repository.CustomerRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
@Profile("dev")
public class CustomerTestDataGenerator {

	private final CustomerRepository customerRepository;

	public CustomerTestDataGenerator(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	private void createIfNotExist(String firstname, String name, String email, String street, String city) {
		if (customerRepository.findByName(name).size() == 0) {
			customerRepository.save(new Customer(firstname, name, email, street, city));
		}
	}

	@PostConstruct
	public void generateTestData() {
		createIfNotExist("Eberhard", "Wolff", "eberhard.wolff@posteo.net", "Unter den Linden", "Berlin");
		createIfNotExist("Rod", "Johnson", "rod@somewhere.com", "Market Street", "San Francisco");
	}

}
