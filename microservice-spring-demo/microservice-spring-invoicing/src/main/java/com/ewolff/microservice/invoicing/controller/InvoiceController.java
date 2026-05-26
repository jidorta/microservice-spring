package com.ewolff.microservice.invoicing.controller;

import com.ewolff.microservice.invoicing.entity.Invoice;
import com.ewolff.microservice.invoicing.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import com.ewolff.microservice.invoicing.service.InvoiceRepository;

@Controller
public class InvoiceController {

	private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }


    @GetMapping(value = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView invoice(@PathVariable long id) {

        Invoice invoice = invoiceService.findById(id)
                .orElseThrow(()->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Invoice not found"
                        ));

        return new ModelAndView(
                "invoice",
                "invoice",
                invoice
        );
	}

	@GetMapping
	public ModelAndView invoiceList() {

        return new ModelAndView(
                "invoicelist",
                "invoices",
                invoiceService.findAll());
	}

}
