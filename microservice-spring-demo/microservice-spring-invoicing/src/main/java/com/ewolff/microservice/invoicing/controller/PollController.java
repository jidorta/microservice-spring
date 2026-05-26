package com.ewolff.microservice.invoicing.controller;

import com.ewolff.microservice.invoicing.service.InvoicePollingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.ewolff.microservice.invoicing.scheduler.InvoicePoller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/poll")
public class PollController {

	private final InvoicePollingService pollingService;

    public PollController(InvoicePollingService pollingService) {
        this.pollingService = pollingService;
    }


    @PostMapping
	public ResponseEntity<String> poll() {
        pollingService.poll();
		return ResponseEntity.ok("Polling executed successfully");
	}

}
