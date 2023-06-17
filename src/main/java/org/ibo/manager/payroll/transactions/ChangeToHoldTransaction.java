package org.ibo.manager.payroll.transactions;

import org.ibo.manager.payroll.models.HoldMethod;
import org.ibo.manager.payroll.models.PaymentMethod;

public class ChangeToHoldTransaction extends ChangeMethodTransaction {
    public ChangeToHoldTransaction( Long empId ) {
        super( empId );
    }

    @Override
    PaymentMethod getPaymentMethod() {
        return new HoldMethod();
    }
}
