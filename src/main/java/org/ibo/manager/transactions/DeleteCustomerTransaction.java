package org.ibo.manager.transactions;

import org.ibo.manager.models.Reservation;
import org.ibo.manager.models.Customer;
import org.ibo.manager.repositories.Database;

/**
 * Used to delete a {@link Customer} (the customer can't have an {@link Reservation})
 */
public class DeleteCustomerTransaction implements Transaction {
    private final Customer customer;

    /**
     * @param customerId the customerId
     * @throws IllegalArgumentException if the customer does't exists of if the customer has a reservation
     */
    public DeleteCustomerTransaction( Long customerId ) {
        customer = Database.getCustomerById( customerId );

        if ( customer == null )
            throw new IllegalArgumentException( "No Customer with id: " + customerId + " found" );

        if ( customer.hasReservation() )
            throw new IllegalArgumentException( "Customer " + customerId + " has a reservation and therefore can't be deleted before deleting the reservation" );
    }

    /**
     * Deletes the customer
     */
    @Override
    public void execute() {
        Database.removeCustomer( customer );
    }
}
