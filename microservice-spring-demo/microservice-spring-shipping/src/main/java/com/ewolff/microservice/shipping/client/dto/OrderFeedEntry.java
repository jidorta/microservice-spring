package com.ewolff.microservice.shipping.client.dto;

import java.time.Instant;


public record OrderFeedEntry (

     long id,

     String link,

     Instant updated
){

}