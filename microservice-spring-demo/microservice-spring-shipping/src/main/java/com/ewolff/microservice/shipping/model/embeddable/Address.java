package com.ewolff.microservice.shipping.model.embeddable;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Address {

	private String street;
	private String zip;
	private String city;

	public Address() {

	}

	public Address(String street, String zip, String city) {

		this.street = street;
		this.zip = zip;
		this.city = city;
	}

	public String getStreet() {
		return street;
	}
	public String getZip() {
		return zip;
	}
	public String getCity() {
		return city;
	}



    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", zip='" + zip + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address that)) return false;
        return Objects.equals(street, that.street)
                && Objects.equals(zip, that.zip)
                && Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, zip, city);
    }

}
