package com.ewolff.microservice.invoicing.service;

import java.time.Instant;
import com.ewolff.microservice.invoicing.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;



public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Instant findTopByOrderByUpdateDesc();

}
