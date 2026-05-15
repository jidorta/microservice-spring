package com.ewolff.microservice.order.formatter;

import java.text.ParseException;
import java.util.Locale;

import com.ewolff.microservice.order.entity.Customer;
import com.ewolff.microservice.order.repository.CustomerRepository;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class CustomerFormatter implements Formatter<Customer> {

	private CustomerRepository customerRepository;

	public CustomerFormatter(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public String print(Customer customer, Locale locale) {
		return customer.getCustomerId().toString();
	}

	@Override
	public Customer parse(String text, Locale locale) throws ParseException {
		return customerRepository.findById(Long.parseLong(text)).get();
	}

}
