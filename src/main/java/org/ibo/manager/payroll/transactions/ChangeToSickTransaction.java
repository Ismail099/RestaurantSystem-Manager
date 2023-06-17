package org.ibo.manager.payroll.transactions;

import org.ibo.manager.payroll.models.MonthlySchedule;
import org.ibo.manager.payroll.models.PaymentClassification;
import org.ibo.manager.payroll.models.PaymentSchedule;
import org.ibo.manager.payroll.models.SickClassification;

public class ChangeToSickTransaction extends ChangeClassificationTransaction {

    public ChangeToSickTransaction( Long empId ) {
        super( empId );
    }

    @Override
    PaymentClassification getPaymentClassification( PaymentClassification oldClassification ) {
        return new SickClassification( oldClassification.calculateCompensation() );
    }

    @Override
    PaymentSchedule getPaymentSchedule( PaymentSchedule oldSchedule ) {
        return new MonthlySchedule();
    }
}
