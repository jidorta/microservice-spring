package com.ewolff.microservice.invoicing.service;

import com.ewolff.microservice.invoicing.entity.Invoice;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {

    void generateInvoice(Invoice invoice);

    Optional<Invoice> findById(Long id);

    List<Invoice> findAll();
}
