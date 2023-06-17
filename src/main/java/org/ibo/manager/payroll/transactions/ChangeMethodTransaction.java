package org.ibo.manager.payroll.transactions;

import org.ibo.manager.payroll.models.Employee;
import org.ibo.manager.payroll.models.PaymentMethod;

public abstract class ChangeMethodTransaction extends ChangeEmployeeTransaction {
    public ChangeMethodTransaction( Long empId ) {
        super( empId );
    }

    @Override
    void change( Employee emp ) {
        emp.setPaymentMethod( getPaymentMethod() );
    }

    abstract PaymentMethod getPaymentMethod();
}
