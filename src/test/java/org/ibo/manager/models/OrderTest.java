package org.ibo.manager.models;

import org.ibo.manager.repositories.Database;
import org.ibo.manager.transactions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class OrderTest {
    private final Long tableId = 1L, customerId = 1L, itemId = 1L;

    @BeforeEach
    void setUpDatabase() {
        Database.deleteAll();
        fillDatabase();
    }

    private void fillDatabase() {
        new AddTableTransaction( tableId ).execute();
        new AddCustomerTransaction( customerId, "Kiddo", "mp4-12cs5@outlook.com", "12345678" ).execute();
        new AddItemTransaction( itemId, "French Fries", 10.0 ).execute();
    }

    @Test
    void testAddOrderToReservation() {
        new ImmediateReservationTransaction( tableId, customerId ).execute();

        AddItemsToServiceTransaction service = new AddItemsToReservationTransaction( tableId );
        service.addItem( itemId );
        service.execute();

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        Reservation reservation = validateReservation( tableReservations, 1, 0, tableId, customerId, true, 10.0 );

        Order order = reservation.getOrder();
        validateOrder( order, 1, 10.0 );
        assertEquals( 1, order.getItemsQuantities().get( Database.getItemById( itemId ) ) );
    }

    @Test
    void testAddOrderToNonActiveReservationInFuture() {
        LocalDate fifthOfNovemberNextYear = LocalDate.of( LocalDate.now().plusYears( 1 ).getYear(), Month.NOVEMBER, 5 );
        LocalTime eightPM = LocalTime.of( 20, 0 );
        new ScheduledReservationTransaction( tableId, customerId, fifthOfNovemberNextYear, eightPM ).execute();

        assertThrows( IllegalArgumentException.class, () -> new AddItemsToReservationTransaction( tableId ) );

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        Reservation reservation = validateReservation( tableReservations, 1, 0, tableId, customerId, false, 10.0 );

        validateOrder( reservation.getOrder(), 0, 0.0 );
    }

    @Test
    void testAddOrderToNonActiveReservationToday() {
        new ScheduledReservationTransaction( tableId, customerId, LocalDate.now(), LocalTime.now() ).execute();

        assertThrows( IllegalArgumentException.class, () -> new AddItemsToReservationTransaction( tableId ) );

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        Reservation reservation = validateReservation( tableReservations, 1, 0, tableId, customerId, false, 10.0 );

        validateOrder( reservation.getOrder(), 0, 0.0 );
    }

    @Test
    void testAddOrderToActiveReservationToday() {
        new ScheduledReservationTransaction( tableId, customerId, LocalDate.now(), LocalTime.now() ).execute();

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        validateReservation( tableReservations, 1, 0, tableId, customerId, false, 10.0 );

        Transaction activateReservation = new ActivateReservationTransaction( tableId );
        activateReservation.execute();

        AddItemsToServiceTransaction service = new AddItemsToReservationTransaction( tableId );
        service.addItem( itemId );
        service.execute();

        tableReservations = Database.getReservationsByTableId( tableId );
        Reservation reservation = validateReservation( tableReservations, 1, 0, tableId, customerId, true, 20.0 );

        validateOrder( reservation.getOrder(), 1, 10.0 );
    }

    @Test
    void testAddTwoOrderToReservation() {
        new ImmediateReservationTransaction( tableId, customerId ).execute();

        AddItemsToServiceTransaction service = new AddItemsToReservationTransaction( tableId );
        service.addItem( itemId );
        service.execute();

        service = new AddItemsToReservationTransaction( tableId );
        service.addItem( itemId );
        service.execute();

        List<Reservation> tableReservations = Database.getReservationsByTableId( tableId );
        Reservation reservation = validateReservation( tableReservations, 1, 0, tableId, customerId, true, 20.0 );

        Order order = reservation.getOrder();
        validateOrder( order, 1, 20.0 );
        assertEquals( 2, order.getItemsQuantities().get( Database.getItemById( itemId ) ) );
    }

    Reservation validateReservation( List<Reservation> reservations, int expectedSize, int indexToCheck, Long tableId, Long customerId, boolean isActive, double total ) {
        assertEquals( expectedSize, reservations.size() );
        Reservation reservation = reservations.get( indexToCheck );
        assertNotNull( reservation );
        assertEquals( customerId, reservation.getCustomerId() );
        assertEquals( tableId, reservation.getReservedTableId() );
        assertEquals( total, reservation.getTotal() );
        assertEquals( isActive, reservation.isActive() );
        return reservation;
    }

    void validateOrder( Order order, int numberOfItems, double total ) {
        assertEquals( numberOfItems, order.getItemsQuantities().size() );
        assertEquals( total, order.getTotal() );
    }
}