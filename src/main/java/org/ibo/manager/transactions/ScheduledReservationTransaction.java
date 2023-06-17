package org.ibo.manager.transactions;

import org.ibo.manager.models.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Creates a scheduled {@link Reservation}
 * a scheduled reservation is used to reserve a table in advance before the customer arrives
 * The customer can't reserve a table in the past therefore
 */
public class ScheduledReservationTransaction extends AddReservationTransaction {
    private static final int MAX_DELAY_SECONDS = 3;
    /**
     * @throws IllegalArgumentException if the customer, table don't exists or if the reservation information like date and table are not valid
     */
    public ScheduledReservationTransaction( Long tableId, Long customerId, LocalDate reservationDate, LocalTime reservationTime ) {
        super( tableId, customerId, reservationDate, reservationTime );
        setReservationFee( 10.0 );

        if ( reservationDate == null || reservationTime == null )
            throw new IllegalArgumentException( "Both date and time are required" );


        if ( !presentOrFuture( reservationDate, reservationTime ) )
            throw new IllegalArgumentException( "Can't make reservations in the past" );
    }

    @Override
    Reservation getReservation() {
        return new Reservation( getTableId(), getCustomerId(), getReservationDate(), getReservationTime(), getReservationFee() );
    }

    /**
     * @param reservationDate date of reservation
     * @param reservationTime time of reservation
     * @return true if the DateTime is in the present of the future (with an accepted 3 seconds delay), false otherwise
     */
    private boolean presentOrFuture( LocalDate reservationDate, LocalTime reservationTime ) {
        LocalDateTime reservationDateTime = LocalDateTime.of( reservationDate, reservationTime );
        LocalDateTime now = LocalDateTime.now().minusSeconds( MAX_DELAY_SECONDS );
        return reservationDateTime.isAfter( now );
    }
}
