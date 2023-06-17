package org.ibo.manager.transactions;

import org.ibo.manager.models.Delivery;
import org.ibo.manager.models.Invoice;
import org.ibo.manager.models.Reservation;
import org.ibo.manager.models.Service;
import org.ibo.manager.repositories.Database;

/**
 * Produces an invoice from a {@link Service}
 */
public class CheckOutTransaction implements Transaction {
    private boolean isReservation = true;
    private Service service;
    private Invoice invoice;

    /**
     * Used to checkout a {@link Delivery} on the selected table
     * @throws IllegalArgumentException if the reservation isn't active or doesn't exists
     */
    public CheckOutTransaction( Long tableId ) {
        Reservation reservation = Database.getCurrentReservationByTableId( tableId );
        if ( reservation == null || !reservation.isActive() )
            throw new IllegalArgumentException( "The reservations isn't active or doesn't exists" );

        service = reservation;
    }

    public CheckOutTransaction( Reservation reservation ) {
        if ( !reservation.isActive() )
            throw new IllegalArgumentException( "The reservations isn't active" );

        service = reservation;
    }

    /**
     * Used to checkout a {@link Delivery}
     */
    public CheckOutTransaction( Delivery delivery ) {
        isReservation = false;

        service = delivery;
    }

    /**
     * Creates the invoice and deletes the reservation
     */
    @Override
    public void execute() {
        if ( isReservation ) {
            Database.deleteReservation( ((Reservation)service).getTable().getId() , service.getDate() );
        }
        else {
            Database.deleteDelivery( (Delivery) service );
        }
        invoice = new Invoice( service, 0.0, isReservation );
    }

    /**
     * This can only be called after using the {@link #execute()} method
     * @return The {@link Invoice} for the reservation
     */
    public Invoice getInvoice() {
        return invoice;
    }
}
