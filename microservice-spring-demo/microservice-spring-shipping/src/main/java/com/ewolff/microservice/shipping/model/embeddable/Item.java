package com.ewolff.microservice.shipping.model.embeddable;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Item {

	private Long itemId;
	private String name;

	public Item() {
		itemId = 0L;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemId,name);
	}

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                '}';
    }
}
