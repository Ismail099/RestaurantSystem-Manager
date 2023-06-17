package org.ibo.manager.payroll.transactions;

import lombok.Getter;
import lombok.Setter;
import org.ibo.manager.payroll.models.HourlyClassification;
import org.ibo.manager.payroll.models.PaymentClassification;
import org.ibo.manager.payroll.models.PaymentSchedule;
import org.ibo.manager.payroll.models.WeeklySchedule;

@Getter
@Setter
public class ChangeToHourlyTransaction extends ChangeClassificationTransaction {
    private double hourlyRate;
    private double overHoursBonusRate = 1.5;
    private int overHoursThreshold = 8;

    public ChangeToHourlyTransaction( Long empId, double hourlyRate ) {
        super( empId );
        this.hourlyRate = hourlyRate;
    }

    @Override
    PaymentClassification getPaymentClassification( PaymentClassification oldClassification ) {
        return new HourlyClassification( hourlyRate, overHoursBonusRate, overHoursThreshold );
    }

    @Override
    PaymentSchedule getPaymentSchedule( PaymentSchedule oldSchedule ) {
        return new WeeklySchedule();
    }
}
