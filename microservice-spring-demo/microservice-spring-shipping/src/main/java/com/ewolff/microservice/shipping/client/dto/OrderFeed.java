package com.ewolff.microservice.shipping.client.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class OrderFeed {

    private Instant updated;

    private List<OrderFeedEntry> orders = new ArrayList<>();

    public OrderFeed() {
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public List<OrderFeedEntry> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderFeedEntry> orders) {
        this.orders = orders != null ? orders : new ArrayList<>();
    }

    @Override
    public int hashCode() {
        return Objects.hash(updated,orders);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof OrderFeed that)) {
            return false;
        }

        return Objects.equals(updated, that.updated)
                && Objects.equals(orders, that.orders);
    }

    @Override
    public String toString() {
        return "OrderFeed{" +
                "updated=" + updated +
                ", orders=" + orders +
                '}';
    }

}