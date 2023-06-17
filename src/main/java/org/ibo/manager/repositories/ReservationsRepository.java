package org.ibo.manager.repositories;

import org.ibo.manager.models.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.ibo.manager.models.Reservation;
import org.ibo.manager.models.ServiceId;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationsRepository extends CrudRepository<Reservation, ServiceId> {
    /**
     * @param customerId the customer id
     * @return a list of all {@link Customer}'s {@link Reservation}s
     */
    List<Reservation> findByServiceIdCustomerId( Long customerId );

    /**
     * @param date the date of the reservations
     * @return a list of {@link Reservation} in the specified date
     */
    List<Reservation> findByServiceIdDate( LocalDate date );

    /**
     * @param tableId the tableId
     * @return a list of {@link Reservation} for the specified table
     */
    List<Reservation> findByTableId( Long tableId );

    List<Reservation> findAllByActiveTrue();
}
