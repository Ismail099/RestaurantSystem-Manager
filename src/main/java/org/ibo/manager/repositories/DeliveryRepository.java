package org.ibo.manager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.ibo.manager.models.Delivery;
import org.ibo.manager.models.ServiceId;

import java.util.List;

public interface DeliveryRepository extends CrudRepository<Delivery, ServiceId> {
    List<Delivery> findByServiceIdCustomerId( Long customerId );
}
