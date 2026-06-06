package com.ewolff.microservice.shipping.scheduler;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import com.ewolff.microservice.shipping.client.dto.OrderFeed;
import com.ewolff.microservice.shipping.client.dto.OrderFeedEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.ewolff.microservice.shipping.entity.Shipment;
import com.ewolff.microservice.shipping.service.ShipmentService;
import io.github.resilience4j.retry.annotation.Retry;

@Component
public class ShippingPoller {

	private static final Logger log = LoggerFactory.getLogger(ShippingPoller.class);

	private final String url;

	private final RestTemplate restTemplate;

	private ZonedDateTime lastModified;

	private final ShipmentService shipmentService;

	private boolean pollingActivated;

	public ShippingPoller(@Value("${order.url}") String url, @Value("${poller.actived:true}") boolean pollingActivated,
			ShipmentService shipmentService, RestTemplate restTemplate) {
		super();
		this.url = url;
		this.shipmentService = shipmentService;
		this.pollingActivated = pollingActivated;
		this.restTemplate = restTemplate;
	}

	@Scheduled(fixedDelay = 30000)
	@Retry(name = "poller")
	public void poll() {
		if (!pollingActivated) {
			return;
		}
	}

	public void pollInternal() {

		HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "*/*");

		if (lastModified != null) {
            headers.setZonedDateTime(HttpHeaders.IF_MODIFIED_SINCE, lastModified);
		}

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<OrderFeed> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                OrderFeed.class
        );

        log.trace("Data has been modified");

        OrderFeed feed = response.getBody();

        if ( feed == null || feed.getOrders() == null){
            log.warn("Feed or orders is null");
            return;
        }

        for (OrderFeedEntry entry : feed.getOrders()){
            if (lastModified == null
                    || entry.updated().isAfter(lastModified.toInstant())
            ) {
                Shipment shipment = restTemplate
                        .getForEntity(entry.link(),Shipment.class)
                        .getBody();
                if (shipment != null){
                    log.trace("Saving shipping {}", shipment.getId());
                    shipmentService.ship(shipment);
                }
            }
        }

        long lastModifiedMillis = response.getHeaders().getLastModified();

        if (lastModifiedMillis > 0) {
            lastModified = ZonedDateTime.ofInstant(
                    Instant.ofEpochMilli(lastModifiedMillis),
                    ZoneId.systemDefault()
            );
        }
    }

}
