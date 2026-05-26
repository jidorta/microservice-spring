package com.ewolff.microservice.invoicing.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@ToString
public class Invoice {

	@Id
	private long id;

	@Embedded
	private Customer customer;

	private Instant updated;

	@Embedded
	private Address billingAddress = new Address();

	@OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
	private List<InvoiceLine> invoiceLines = new ArrayList<>();



    public Invoice(Customer customer, Instant updated, Address billingAddress, List<InvoiceLine> invoiceLines) {
        this.customer = customer;
        this.updated = updated;
        this.billingAddress = billingAddress;
        this.invoiceLines = invoiceLines;
    }

    public Invoice(Long i, Customer customer, Instant updated, Address monheimAmRhein, ArrayList<InvoiceLine> invoiceLines) {
    }

    public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public Instant getUpdated() {
		return updated;
	}

	public void setUpdated(Instant created) {
		this.updated = created;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


    public List<InvoiceLine> getInvoiceLines() {
        return invoiceLines;
    }

    public void setInvoiceLines(List<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

    public void addLine(int count, Item item) {
		invoiceLines.add(new InvoiceLine(count,item));
	}


	public int getNumberOfLines() {
		return invoiceLines.size();
	}

	public BigDecimal totalAmount() {
		return invoiceLines.stream()
                .map(InvoiceLine::totalAmount)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
	}

	@Override
    public int hashCode(){
        return Objects.hash(id);
    }
    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Invoice)) {
            return false;
        }

        Invoice invoice = (Invoice) o;

        return Objects.equals(id, invoice.id);
    }


}
