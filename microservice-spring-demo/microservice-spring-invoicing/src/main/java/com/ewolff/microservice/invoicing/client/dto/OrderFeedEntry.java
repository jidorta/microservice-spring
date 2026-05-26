package com.ewolff.microservice.invoicing.client.dto;

import java.net.URI;
import java.time.Instant;
import java.util.Objects;


public class OrderFeedEntry {

    private long id;


    private URI link;

    private Instant updated;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public URI getLink() {
        return link;
    }

    public void setLink(URI link) {
        this.link = link;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderFeedEntry)) return false;

        OrderFeedEntry that = (OrderFeedEntry) o;

        return id == that.id &&
                Objects.equals(link, that.link) &&
                Objects.equals(updated, that.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, link, updated);
    }

    @Override
    public String toString() {
        return "OrderFeedEntry{" +
                "id=" + id +
                ", link='" + link + '\'' +
                ", updated=" + updated +
                '}';
    }

    
    
}