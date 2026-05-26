package com.ewolff.microservice.invoicing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {

	@Column(nullable = false)
	private Long itemId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private BigDecimal price;

    public Item(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
}
