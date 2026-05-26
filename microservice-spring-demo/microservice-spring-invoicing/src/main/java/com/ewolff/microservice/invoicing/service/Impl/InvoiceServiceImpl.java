package com.ewolff.microservice.invoicing.service.Impl;

import com.ewolff.microservice.invoicing.entity.Invoice;
import com.ewolff.microservice.invoicing.repository.InvoiceService;
import com.ewolff.microservice.invoicing.service.InvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	private static final Logger log = LoggerFactory.getLogger(InvoiceServiceImpl.class);

	private final InvoiceRepository invoiceRepository;

	public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
		this.invoiceRepository = invoiceRepository;
	}

	@Override
	@Transactional
	public void generateInvoice(Invoice invoice) {

        try{
            invoiceRepository.save(invoice);

            log.info("Invoice {} saved",invoice.getId());

        }catch (DataIntegrityViolationException ex){
            log.info("Invoice {} alredy exits - ignored",
                    invoice.getId());
        }
	}

    @Transactional(readOnly = true)
    @Override
    public Optional<Invoice> findById(Long id) {
        return invoiceRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }
}
