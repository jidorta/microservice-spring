package com.ewolff.microservice.shipping.model.embeddable;


import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Customer {


	private Long customerId;
	private String name;
	private String firstname;

	public Customer() {
		customerId = 0L;
	}

	public Customer(long customerId, String firstname, String name) {
		this.customerId = customerId;
		this.name = name;
		this.firstname = firstname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long id) {
		this.customerId = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerId,firstname,name);
	}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Customer other)) {
            return false;
        }

        return Objects.equals(customerId, other.customerId)
                && Objects.equals(firstname, other.firstname)
                && Objects.equals(name, other.name);
    }

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", name=" + name + ", firstname=" + firstname + "]";
	}


	
	
}
