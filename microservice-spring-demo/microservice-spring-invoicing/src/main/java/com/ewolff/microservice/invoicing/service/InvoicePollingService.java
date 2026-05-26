package com.ewolff.microservice.invoicing.service;

import com.ewolff.microservice.invoicing.repository.InvoiceService;
import com.ewolff.microservice.invoicing.client.order.OrderClient;
import com.ewolff.microservice.invoicing.client.dto.OrderFeed;
import com.ewolff.microservice.invoicing.client.dto.OrderFeedEntry;
import com.ewolff.microservice.invoicing.entity.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;


@Service
public class InvoicePollingService {

    private static final Logger log =
            LoggerFactory.getLogger(InvoicePollingService.class);

    private final OrderClient orderClient;
    private final InvoiceService invoiceService;
    private ZonedDateTime lastModified;

    public InvoicePollingService(OrderClient orderClient, InvoiceService invoiceService) {
        this.orderClient = orderClient;
        this.invoiceService = invoiceService;
    }

    public void poll(){

        ResponseEntity<OrderFeed> response =
                orderClient.getOrders(lastModified);

        if (response.getStatusCode() == HttpStatus.NOT_MODIFIED){
            log.trace("No new data");
            return;
        }
        processFeed(response.getBody());

        updateLastModified(response);
    }

    private void processFeed(OrderFeed feed){

        if (feed == null || feed.getOrders() == null){
            return;
        }

        for (OrderFeedEntry entry : feed.getOrders()){

            if (isNewEntry(entry)){

                Invoice invoice =
                        orderClient.getInvoice(entry.getLink());

                log.trace("Saving invoice {}", invoice.getId());

                invoiceService.generateInvoice(invoice);
            }
        }
    }

    private boolean isNewEntry(OrderFeedEntry entry){

        return lastModified == null
                || entry.getUpdated()
                        .isAfter(lastModified.toInstant());
    }

    private void updateLastModified(

                ResponseEntity<OrderFeed> response){

        String header =
                response.getHeaders()
                        .getFirst(HttpHeaders.LAST_MODIFIED);

        if(header != null){

            lastModified =
                    response.getHeaders()
                            .getFirstZonedDateTime(
                                    HttpHeaders.LAST_MODIFIED);

            log.trace("Last modified update {}", lastModified);
        }
    }

}
