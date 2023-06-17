package org.ibo.manager.payroll.transactions;

import org.ibo.manager.payroll.models.*;
import org.ibo.manager.repositories.Database;
import org.ibo.manager.transactions.Transaction;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This Transaction adds a {@link TimeCard} to an hourly employee
 * A TimeCard records how many hours an employee worked on a specified date
 */
public class AddTimeCardTransaction implements Transaction {
    private final LocalDate date;
    private final LocalTime timeWorked;
    private final HourlyClassification hourlyClassification;

    /**
     * @throws IllegalArgumentException if the Employee doesn't exists or if the employee isn't hourly
     */
    public AddTimeCardTransaction( Long empId, LocalDate date, LocalTime timeWorked ) {
        Employee emp = Database.getEmployeeById( empId );
        if ( emp == null )
            throw new IllegalArgumentException( "No such Employee exists" );

        PaymentClassification paymentClassification = emp.getPaymentClassification();
        if ( !( paymentClassification instanceof HourlyClassification ) )
            throw new IllegalArgumentException( "Can't add time card to non-hourly employees" );

        hourlyClassification = (HourlyClassification)paymentClassification;

        this.date = date;
        this.timeWorked = timeWorked;
    }

    @Override
    public void execute() {
        TimeCard timeCard = new TimeCard( new TimeCardId( hourlyClassification.getId(), date ), timeWorked );
        Database.addTimeCard( timeCard );
    }
}
