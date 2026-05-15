package com.ewolff.microservice.order.bootstrap;

import com.ewolff.microservice.order.dto.OrderFeedEntry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderFeed {

    private LocalDateTime updated;

    private List<OrderFeedEntry> orders;

    public OrderFeed() {
        super();
    }

    public OrderFeed(LocalDateTime updated) {
        super();
        this.updated = updated;
        orders = new ArrayList<OrderFeedEntry>();
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public List<OrderFeedEntry> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderFeedEntry> orders) {
        this.orders = orders;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orders == null) ? 0 : orders.hashCode());
		result = prime * result + ((updated == null) ? 0 : updated.hashCode());
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
		OrderFeed other = (OrderFeed) obj;
		if (orders == null) {
			if (other.orders != null)
				return false;
		} else if (!orders.equals(other.orders))
			return false;
		if (updated == null) {
			if (other.updated != null)
				return false;
		} else if (!updated.equals(other.updated))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderFeed [updated=" + updated + ", orders=" + orders + "]";
	}

    
    
}