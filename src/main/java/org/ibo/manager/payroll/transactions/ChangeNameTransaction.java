package org.ibo.manager.payroll.transactions;

import org.ibo.manager.payroll.models.Employee;

public class ChangeNameTransaction extends ChangeEmployeeTransaction {
    private final String newName;

    public ChangeNameTransaction( Long empId, String newName ) {
        super( empId );
        this.newName = newName;
    }


    @Override
    void change( Employee emp ) {
        emp.setName( newName );
    }
}
