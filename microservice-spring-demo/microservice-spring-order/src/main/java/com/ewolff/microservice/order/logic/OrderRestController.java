package com.ewolff.microservice.order.logic;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.StreamSupport;

import com.ewolff.microservice.order.dto.Order;
import com.ewolff.microservice.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.ewolff.microservice.order.OrderFeed;
import com.ewolff.microservice.order.OrderFeedEntry;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderRestController {

	private final OrderRepository orderRepository;

    @GetMapping(value = "/feed", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderFeed> orderFeed(
            WebRequest webRequest,
            HttpServletRequest request){

        Date lastUpdate = orderRepository.lastUpdate();

        if (lastUpdate != null &&
               webRequest.checkNotModified(lastUpdate.getTime())){
            log.trace("Not Modified returned - request with If-Modified-Since {}",
                    webRequest.getHeader(HttpHeaders.IF_MODIFIED_SINCE));
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        log.trace("Returned Feed");

        String baseUrl = buildBaseUrl(request);

        List<OrderFeedEntry> entries =
                StreamSupport.stream(orderRepository.findAll().spliterator(),false)
                        .map(order -> new OrderFeedEntry(
                                order.getId(),
                                baseUrl + "order/" + order.getId(),
                                order.getUpdated()))
                        .toList();
        OrderFeed feed = new OrderFeed(lastUpdate);
        feed.setOrders(entries);

        return ResponseEntity.ok(feed);
    }

    private String buildBaseUrl(HttpServletRequest request){
        return "%s://%s:%d%s/".formatted(
                request.getScheme(),
                request.getServerName(),
                request.getServerPort(),
                request.getContextPath()
        );
    }

	private String baseUrl(HttpServletRequest request) {
		return "%s://%s:%d%s/".formatted(request.getScheme(), request.getServerName(), request.getServerPort(),
				request.getContextPath());
	}

}