package com.ewolff.microservice.shipping.service;

import com.ewolff.microservice.shipping.entity.Shipment;

public interface ShipmentService {

	void ship(Shipment shipment);

}