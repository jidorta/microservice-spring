package com.ewolff.microservice.order.logic;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class OrderController {

	private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping("/")
	public ModelAndView orderList() {
        return new ModelAndView("orderlist", "orders", orderService.getAllOrders());
	}

	@GetMapping("/form.html")
	public ModelAndView form() {
		return orderService.getOrderForm();
	}

	@PostMapping("/line")
	public ModelAndView addLine(Order order) {
		return orderService.addLine(order);
	}

	@GetMapping("/{id}")
	public ModelAndView get(@PathVariable long id) {
		return new ModelAndView("order", "order", orderService.getOrder(id));
	}

    @GetMapping("/order/{id}")
    public ResponseEntity<Order>getJSON(@PathVariable long id){
        return orderService.getOrderResponse(id);
    }

	@GetMapping("/full-{id}")
    public ModelAndView full(@PathVariable long id) {
       return new ModelAndView("order-full","order",orderService.getOrder(id));
	}

	@PostMapping("/")
	public ModelAndView post(Order order) {
        log.info("Creating new order");
		orderService.createOrder(order);
		return new ModelAndView("success");
	}

	@DeleteMapping("/{id}")
	public ModelAndView delete(@PathVariable long id) {
        log.info("Deleting order with id {} " ,id);
		orderService.deleteOrder(id);
		return new ModelAndView("success");
	}

}
