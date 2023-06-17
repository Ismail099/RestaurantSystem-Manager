package org.ibo.manager.payroll.transactions;

import org.ibo.manager.payroll.models.MonthlySchedule;
import org.ibo.manager.payroll.models.PaymentClassification;
import org.ibo.manager.payroll.models.PaymentSchedule;
import org.ibo.manager.payroll.models.SalariedClassification;

public class ChangeToSalariedTransaction extends ChangeClassificationTransaction {
    private final double salary;

    public ChangeToSalariedTransaction( Long empId, double salary ) {
        super( empId );
        this.salary = salary;
    }

    @Override
    PaymentClassification getPaymentClassification( PaymentClassification oldClassification ) {
        return new SalariedClassification( salary );
    }

    @Override
    PaymentSchedule getPaymentSchedule( PaymentSchedule oldSchedule ) {
        return new MonthlySchedule();
    }
}
