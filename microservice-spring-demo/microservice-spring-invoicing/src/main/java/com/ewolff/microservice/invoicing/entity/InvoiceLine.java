package com.ewolff.microservice.invoicing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Entity
public class InvoiceLine {

    @Id
    @GeneratedValue
    private long id;

    @Positive
	@Column(name = "F_COUNT",nullable = false)
	private int count;

    private BigDecimal price;

	@Embedded
	private Item item;


	public void setCount(int count) {
		this.count = count;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public InvoiceLine() {
	}

	public InvoiceLine(int count, Item item) {
		this.count = count;
		this.item = item;

	}

    public InvoiceLine(int count, BigDecimal price, Item item) {
        this.count = count;
        this.price = price;
        this.item = item;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCount() {
		return count;
	}

	public Item getItem() {
		return item;
	}

	public BigDecimal totalAmount() {

        return item.getPrice()
                .multiply(BigDecimal.valueOf(count));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvoiceLine other = (InvoiceLine) obj;
		if (count != other.count)
			return false;
		if (id != other.id)
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InvoiceLine [count=" + count + ", item=" + item + ", id=" + id + "]";
	}

}
