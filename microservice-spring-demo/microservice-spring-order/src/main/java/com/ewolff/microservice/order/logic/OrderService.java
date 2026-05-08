package com.ewolff.microservice.order.logic;

import java.util.Date;
import java.util.Optional;

import com.ewolff.microservice.order.customer.Customer;
import com.ewolff.microservice.order.customer.CustomerRepository;
import com.ewolff.microservice.order.item.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Service
public class OrderService {

	private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;


    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
    }

    public Order getAllOrders(long id){
        log.info("Fetching all orders");

        return orderRepository.findAll();

    }

    public Order getOrder(long id){

        log.info("Fetching order with id {}",id);

        return orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Order not found with id {}", id);
                    return new RuntimeException("Order not found");

                });
    }

    public ResponseEntity<Order> getOrderResponse(long id){
        log.info("Fetching order response with id {}",id);

        Optional<Order> response  = orderRepository.findById(id);

        if (response.isEmpty()){
            log.warn("Order not found with id {}",id);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        log.info("Order found with id {}" , id);

        return new ResponseEntity<>(response.get(), HttpStatus.OK);
    }

    public  ModelAndView getOrderForm(){

        log.info("Loading order form");

        ModelAndView modelAndView =
                new ModelAndView("orderForm", "order", new Order());

        modelAndView.addObject(
                "items",
                itemRepository.findAll(Sort.unsorted()));

        modelAndView.addObject(
                "customers",
                itemRepository.findAll(Sort.unsorted()));

        return modelAndView;
    }

    public ModelAndView addLine(Order order){

        log.info("Adding line to order");

        order.addLine(
                0,
                itemRepository.findAll(Sort.unsorted())
                        .iterator()
                        .next());

        ModelAndView modelAndView =
                new ModelAndView("orderForm", "order",order);

        modelAndView.addObject(
                "items",
                itemRepository.findAll(Sort.unsorted()));

        modelAndView.addObject(
                "customers",
                customerRepository.findAll(Sort.unsorted()));

        return modelAndView;
    }

    public Order createOrder(Order order){

        log.info("Creating order");

        return orderRepository.save(order);
    }

    public void deleteOrder(long id){

        log.info("Deleting order with id {}", id);
        orderRepository.deleteById(id);
    }
}
