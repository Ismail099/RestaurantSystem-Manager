package org.ibo.manager.payroll.repositories;

import org.springframework.data.repository.CrudRepository;
import org.ibo.manager.payroll.models.TimeCard;
import org.ibo.manager.payroll.models.TimeCardId;

public interface TimeCardRepository extends CrudRepository<TimeCard, TimeCardId> {
}
