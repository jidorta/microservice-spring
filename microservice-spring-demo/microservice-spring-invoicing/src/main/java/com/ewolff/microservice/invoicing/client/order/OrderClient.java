package com.ewolff.microservice.invoicing.client.order;

import com.ewolff.microservice.invoicing.client.dto.OrderFeed;
import com.ewolff.microservice.invoicing.entity.Invoice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;

@Component
public class OrderClient {

    private final RestTemplate restTemplate;
    private final String url;


    public OrderClient(RestTemplate restTemplate,@Value("${order.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public ResponseEntity<OrderFeed> getOrders(ZonedDateTime lastModified){

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        if(lastModified != null){
            headers.setZonedDateTime(
                    HttpHeaders.IF_MODIFIED_SINCE,
                    lastModified
            );
        }

        return  restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                OrderFeed.class
        );

    }

    public Invoice getInvoice(URI link){
        return restTemplate.getForObject(link, Invoice.class);
    }


}
