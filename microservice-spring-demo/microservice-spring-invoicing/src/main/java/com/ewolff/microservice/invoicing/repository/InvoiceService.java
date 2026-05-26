package com.ewolff.microservice.invoicing.repository;

import com.ewolff.microservice.invoicing.entity.Invoice;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {

	void generateInvoice(Invoice invoice);

    @Transactional(readOnly = true)
    Optional<Invoice> findById(Long id);

    @Transactional(readOnly = true)
    List<Invoice> findAll();
}