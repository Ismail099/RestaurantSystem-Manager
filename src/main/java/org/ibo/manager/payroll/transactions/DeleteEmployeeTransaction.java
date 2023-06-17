package org.ibo.manager.payroll.transactions;

import org.ibo.manager.repositories.Database;
import org.ibo.manager.transactions.Transaction;

public class DeleteEmployeeTransaction implements Transaction {
    private final Long empId;

    /**
     * @throws IllegalArgumentException if the customer doesn't exists
     */
    public DeleteEmployeeTransaction( Long empId ) {
        if ( Database.getEmployeeById( empId ) == null )
            throw new IllegalArgumentException( "No such Employee exists" );
        this.empId = empId;
    }

    @Override
    public void execute() {
        Database.removeEmployeeById( empId );
    }
}
