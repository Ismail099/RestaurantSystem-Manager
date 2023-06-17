package org.ibo.manager.payroll.transactions;

import org.ibo.manager.repositories.Database;
import org.ibo.manager.transactions.Transaction;
import org.ibo.manager.payroll.models.Employee;
import org.ibo.manager.payroll.models.PayCheck;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This transaction finds all employees that can be paid on the passed date
 * it stores all paychecks in it so they can be verified.
 */
public class PayDayTransaction implements Transaction {
    private final LocalDate payDate;
    private final Map<Long, PayCheck> payChecks;

    /**
     * @throws IllegalArgumentException if the passed date is in the past
     */
    public PayDayTransaction( LocalDate payDate ) {
        if ( LocalDate.now().isAfter( payDate ) )
            throw new IllegalArgumentException( "Can't pay for past days" );

        this.payDate = payDate;
        this.payChecks = new HashMap<>();
    }

    @Override
    public void execute() {
        List<Employee> employees = Database.getEmployees();

        for ( Employee employee : employees )
            if ( employee.isPayDay( payDate ) )
                payChecks.put( employee.getId(), employee.payDay( payDate ) );
    }

    /**
     * @return returns the paycheck for the employee specified by the id, or null if the employee was not payed
     */
    public PayCheck getPayCheck( Long empId ) {
        return payChecks.get( empId );
    }

    /**
     * Must be called after the invoking {@link #execute()}
     * @return All paychecks that were created by this transaction
     */
    public List<PayCheck> getPayChecks() {
        return new ArrayList<>( payChecks.values() );
    }
}
