package com.ewolff.microservice.shipping.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.ewolff.microservice.shipping.model.embeddable.Customer;
import com.ewolff.microservice.shipping.model.embeddable.Address;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Shipment {

	@Id
	private long id;

	@Embedded
	private Customer customer;

	private Instant updated;

	@Embedded
	private Address shippingAddress = new Address();

	@OneToMany(mappedBy ="shipment", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ShipmentLine> shipmentLine = new ArrayList<>();

	private String deliveryService;

	private int cost;

	public Shipment() {
	}

	public Shipment(long id, Customer customer, Instant updated, Address shippingAddress, List<ShipmentLine> shipmentLine,
			String deliveryService) {

		this.id = id;
		this.customer = customer;
		this.updated = updated;
		this.shippingAddress = shippingAddress;
		this.shipmentLine = shipmentLine != null ? shipmentLine : new ArrayList<>();
		this.deliveryService = deliveryService;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getDeliveryService() {
		return deliveryService;
	}

	public void setDeliveryService(String deliveryService) {
		this.deliveryService = deliveryService;
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

	public void setCustomer(Customer customerId) {
		this.customer = customerId;
	}

	public List<ShipmentLine> getShipmentLine() {
		return shipmentLine;
	}

	public Shipment(Customer customer) {
		this.customer = customer;
		this.shipmentLine = new ArrayList<ShipmentLine>();
	}

	public void setShipmentLine(List<ShipmentLine> shipmentLine) {
		this.shipmentLine = shipmentLine;
	}

	public void setOrderLine(List<ShipmentLine> orderLine) {
		this.shipmentLine = orderLine;
	}

	public int getNumberOfLines() {
		return shipmentLine.size();
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getCost() {
		return cost;
	}

    public void calculateShippingCost() {
        if ("DHL".equalsIgnoreCase(deliveryService)) {
            this.cost = 1;
        } else if ("Hermes".equalsIgnoreCase(deliveryService)) {
            this.cost = 2;
        } else {
            throw new IllegalArgumentException("Unknown Delivery Service!");
        }
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shipment that)) return false;
        return id == that.id;
    }

	
}
