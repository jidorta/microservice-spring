package com.ewolff.microservice.invoicing.scheduler;

import com.ewolff.microservice.invoicing.service.InvoicePollingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import io.github.resilience4j.retry.annotation.Retry;

@Component
public class InvoicePoller {

    private final InvoicePollingService pollingService;

    public InvoicePoller(InvoicePollingService pollingService) {
        this.pollingService = pollingService;
    }


    @Scheduled(fixedDelayString = "${poller.delay:30000}")
    @Retry(name = "poller")
    public void poll(){
        pollingService.poll();
    }
}
