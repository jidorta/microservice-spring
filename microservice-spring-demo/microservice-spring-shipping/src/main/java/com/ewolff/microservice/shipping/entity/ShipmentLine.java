package com.ewolff.microservice.shipping.entity;

import com.ewolff.microservice.shipping.model.embeddable.Item;
import jakarta.persistence.*;

@Entity
public class ShipmentLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	@Column(name = "F_COUNT")
	private int count;

	@Embedded
	private Item item;

   @ManyToOne(fetch = FetchType.LAZY)
   private Shipment shipment;

    public ShipmentLine() {
    }

    public ShipmentLine(int count, Item item) {
        this.count = count;
        this.item = item;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public void setCount(int count) {
		this.count = count;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getCount() {
		return count;
	}

	public Item getItem() {
		return item;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shipment that)) return false;
        return id == that.getId();
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

}
