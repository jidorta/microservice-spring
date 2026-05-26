package com.ewolff.microservice.invoicing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

	@Column(nullable = false)
	private Long customerId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String firstname;

	@NotBlank
    @Email
	private String email;

    public Customer(Long customerId, String name, String firstname, String email) {
        this.customerId = customerId;
        this.name = name;
        this.firstname = firstname;
        this.email = email;
    }


}
