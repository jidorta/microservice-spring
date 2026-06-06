package com.ewolff.microservice.shipping.service;

import java.time.Instant;

import com.ewolff.microservice.shipping.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShipmentRepository extends JpaRepository<Shipment, Long>  {

	@Query("SELECT max(s.updated) FROM Shipment s")
    Instant findLastUpdate();

}
