package com.ewolff.microservice.shipping.service.impl;

import com.ewolff.microservice.shipping.entity.Shipment;
import com.ewolff.microservice.shipping.service.ShipmentRepository;
import com.ewolff.microservice.shipping.service.ShipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShipmentServiceImpl implements ShipmentService {

	private static final Logger log = LoggerFactory.getLogger(ShipmentServiceImpl.class);

	private final ShipmentRepository shipmentRepository;

	public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
		this.shipmentRepository = shipmentRepository;
	}

	@Override
	@Transactional
	public void ship(Shipment shipment) {

        shipment.calculateShippingCost();

        try{
            shipmentRepository.save(shipment);
        }catch (DataIntegrityViolationException e){
            log.info("Shipment id {} already exists - ignored", shipment.getId());
        }

	}

}
