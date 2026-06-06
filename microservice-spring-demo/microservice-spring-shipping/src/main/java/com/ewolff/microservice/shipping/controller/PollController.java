package com.ewolff.microservice.shipping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import com.ewolff.microservice.shipping.scheduler.ShippingPoller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PollController {

	private final ShippingPoller poller;

	public PollController(ShippingPoller poller) {
		this.poller = poller;
	}

	@PostMapping("/poll")
	public ResponseEntity<String> poll() {
		poller.poll();
		return ResponseEntity.ok("success");
	}

}
