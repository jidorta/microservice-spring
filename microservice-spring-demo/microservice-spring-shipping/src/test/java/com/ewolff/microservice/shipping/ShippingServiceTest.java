package com.ewolff.microservice.shipping;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import com.ewolff.microservice.shipping.entity.Shipment;
import com.ewolff.microservice.shipping.entity.ShipmentLine;
import com.ewolff.microservice.shipping.model.embeddable.Address;
import com.ewolff.microservice.shipping.model.embeddable.Customer;
import com.ewolff.microservice.shipping.service.ShipmentRepository;
import com.ewolff.microservice.shipping.service.ShipmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ShippingTestApp.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class ShippingServiceTest {

	@Autowired
	private ShipmentRepository shipmentRepository;

	@Autowired
	private ShipmentService shipmentService;

	@Test
	void ensureIdempotencySecondCallIgnored() {
		long countBefore = shipmentRepository.count();
		Shipment shipment = new Shipment(42L,
				new Customer(23L, "Eberhard", "Wolff"),
                Instant.ofEpochMilli(0L), new Address("Krischstr. 100", "40789", "Monheim am Rhein"),
				new ArrayList<ShipmentLine>(), "DHL");
		shipmentService.ship(shipment);
		assertThat(shipmentRepository.count(), is(countBefore + 1));
		assertThat(shipmentRepository.findById(42L).get().getUpdated().toEpochMilli(), equalTo(0L));
		shipment = new Shipment(42,
				new Customer(23L, "Eberhard", "Wolff"),
                Instant.ofEpochMilli(0L), new Address("Krischstr. 100", "40789", "Monheim am Rhein"), new ArrayList<ShipmentLine>(),
				"DHL");
		shipmentService.ship(shipment);
		assertThat(shipmentRepository.count(), is(countBefore + 1));
		assertThat(shipmentRepository.findById(42L).get().getUpdated().toEpochMilli(), equalTo(0L));
	}

	@Test
	void ensureShipmentRateCalculted() {
		Shipment shipment = new Shipment(43L,
				new Customer(23L, "Eberhard", "Wolff"),
                Instant.ofEpochMilli(0L), new Address("Krischstr. 100", "40789", "Monheim am Rhein"),
				new ArrayList<ShipmentLine>(), "DHL");
		shipmentService.ship(shipment);
		assertThat(shipment.getCost(), is(1));
	}

	@Test
	void ensureUnkownShipmentError() {
		Shipment shipment = new Shipment(44L,
				new Customer(23L, "Eberhard", "Wolff"),
                Instant.ofEpochMilli(0L), new Address("Krischstr. 100", "40789", "Monheim am Rhein"),
				new ArrayList<ShipmentLine>(), "Unkown Service");
		assertThrows(IllegalArgumentException.class, () -> shipmentService.ship(shipment));
	}

}
