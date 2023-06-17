package org.ibo.manager.payroll.transactions;

import org.ibo.manager.repositories.Database;
import org.ibo.manager.transactions.Transaction;
import org.ibo.manager.payroll.models.Employee;

public abstract class ChangeEmployeeTransaction implements Transaction {
    private final Long empId;

    public ChangeEmployeeTransaction( Long empId ) {
        this.empId = empId;
    }

    abstract void change( Employee emp );

    @Override
    public void execute() {
        Employee emp = Database.getEmployeeById( empId );
        change( emp );
        Database.addEmployee( emp );
    }
}
