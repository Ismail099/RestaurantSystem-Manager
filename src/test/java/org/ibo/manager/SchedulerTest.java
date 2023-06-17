package org.ibo.manager;

import lombok.SneakyThrows;
import org.ibo.manager.models.Reservation;
import org.ibo.manager.repositories.Database;
import org.ibo.manager.services.WaiterChatService;
import org.ibo.manager.transactions.AddCustomerTransaction;
import org.ibo.manager.transactions.AddTableTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class SchedulerTest {
    private final Long tableId = 1L, tableId2 = 2L, customerId = 1L, customerId2 = 2L;
    private final WaiterChatService waiterChatService;
    private final Scheduler scheduler;

    @Autowired
    public SchedulerTest( WaiterChatService waiterChatService, Scheduler scheduler ) {
        this.waiterChatService = waiterChatService;
        this.scheduler = scheduler;
    }

    @BeforeEach
    void setUpDatabase() {
        Database.deleteAll();
        fillDatabase();
    }

    private void fillDatabase() {
        new AddTableTransaction( tableId ).execute();
        new AddTableTransaction( tableId2 ).execute();
        new AddCustomerTransaction( customerId, "Kiddo", "mp4-12cs5@outlook.com", "12345678" ).execute();
        new AddCustomerTransaction( customerId2, "Kiddo2", "mp412cs5@outlook.com", "12345678" ).execute();
    }

    @Test
    void testOverdueReservation() {
        LocalDateTime twentyMinutesOverdue = LocalDateTime.now().minusMinutes( 20 );
        // Skipping Transaction because it won't allow creating overdue reservations
        Reservation reservation = new Reservation( tableId, customerId, twentyMinutesOverdue.toLocalDate(), twentyMinutesOverdue.toLocalTime(), 0.0 );
        assertTrue( reservation.isOverdue() );

        Database.addReservation( reservation );
        // I really can't wait for 15 minutes in the tests
        scheduler.handleOverdueReservations();
        assertEquals( 0, Database.getReservations().size() );
    }

    @SneakyThrows
    @Test
    void testWaiterAcknowledgmentTimeOut() {
        // This tests is kind of redundant and not needed. but it will stay so no one modify the Scheduler
        scheduler.trySetAcknowledgementTimedOut();
        assertTrue( waiterChatService.isAcknowledgementTimedOut() );
        waiterChatService.setAcknowledged();

        scheduler.trySetAcknowledgementTimedOut();
        assertFalse( waiterChatService.isAcknowledgementTimedOut() );
        Thread.sleep( WaiterChatService.TIME_OUT_DURATION.toMillis() );
        scheduler.trySetAcknowledgementTimedOut();
        assertTrue( waiterChatService.isAcknowledgementTimedOut() );
        assertFalse( waiterChatService.isOnline() );
        assertFalse( waiterChatService.isAcknowledgedNow() );
    }
}