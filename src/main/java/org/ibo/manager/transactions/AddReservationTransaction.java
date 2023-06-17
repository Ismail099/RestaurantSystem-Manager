package org.ibo.manager.transactions;

import lombok.Getter;
import lombok.Setter;
import org.ibo.manager.models.Customer;
import org.ibo.manager.models.Reservation;
import org.ibo.manager.models.Table;
import org.ibo.manager.repositories.Database;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Used to add a reservation. Adding a reservation to a table that is reserved on the same date
 * will throws in {@link IllegalArgumentException}.
 * And adding a reservation to a customer that has a reservation on the same day will also throws
 * an {@link IllegalArgumentException}
 * And of course tables, customers that doesn't exists will also throw this exception
 */
@Getter
public abstract class AddReservationTransaction implements Transaction {
    private Long tableId;
    private Long customerId;
    private final LocalDate reservationDate;
    private final LocalTime reservationTime;
    @Setter
    private double reservationFee;

    /**
     *
     * @param tableId Must exists
     * @param customerId Must exists
     * @param reservationDate date of reservation
     * @param reservationTime time of reservation
     * @throws IllegalArgumentException if the table doesn't exists or is reserved, if the customer doesn't exists or already has a reservation
     */
    public AddReservationTransaction( Long tableId, Long customerId, LocalDate reservationDate, LocalTime reservationTime ) {
        setTableId( tableId, reservationDate );
        setCustomerId( customerId, reservationDate );
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }

    /**
     * Make sure the table exists and that the table is free on the specified date
     * @param tableId table id
     * @param reservationDate date of reservation
     * @throws IllegalArgumentException if the table doesn't exists or is reserved
     */
    private void setTableId( Long tableId, LocalDate reservationDate ) {
        Table table = Database.getTableById( tableId );
        if ( table == null )
            throw new IllegalArgumentException( "No table exists with an id of " + tableId );

        if ( table.isReserved( reservationDate ) )
            throw new IllegalArgumentException( "Table " + tableId + " is already reserved" );

        this.tableId = tableId;
    }

    /**
     * Makes sure the customer exists and that he has the specified reservation on that day for that table
     * @param customerId customer id
     * @param reservationDate date of reservation
     * @throws IllegalArgumentException if the customer doesn't exists, or if he doesn't have any reservation for that table on the selected date
     */
    private void setCustomerId( Long customerId, LocalDate reservationDate ) {
        Customer customer = Database.getCustomerById( customerId );

        if ( customer == null )
            throw new IllegalArgumentException( "No customer exists with an id of " + customerId );

        for ( Reservation reservation : Database.getReservationsByCustomerId( customerId ) ) {
            if ( reservation.getDate().equals( reservationDate ) )
                throw new IllegalArgumentException( "Customer " + customerId + " can't make more than one reservation on the same day" );
        }

        this.customerId = customerId;
    }

    /**
     * Adds the reservation
     */
    @Override
    public void execute() {
        Database.addReservation( getReservation() );
    }

    /**
     * @return The reservation with the specified information
     */
    abstract Reservation getReservation();
}
