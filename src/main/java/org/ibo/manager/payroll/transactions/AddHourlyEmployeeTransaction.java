package org.ibo.manager.payroll.transactions;

import lombok.Getter;
import lombok.Setter;
import org.ibo.manager.payroll.models.HourlyClassification;
import org.ibo.manager.payroll.models.PaymentClassification;
import org.ibo.manager.payroll.models.PaymentSchedule;
import org.ibo.manager.payroll.models.WeeklySchedule;

@Getter
@Setter
public class AddHourlyEmployeeTransaction extends AddEmployeeTransaction {
    private double hourlyRate;
    private double overHoursBonusRate = 1.5;
    private int overHoursThreshold = 8;

    /**
     * @throws IllegalArgumentException if an employee with this id already exists
     */
    public AddHourlyEmployeeTransaction( Long empId, String name, double hourlyRate ) {
        super( empId, name );
        this.hourlyRate = hourlyRate;
    }

    @Override
    PaymentClassification getPaymentClassification() {
        return new HourlyClassification( hourlyRate, overHoursBonusRate, overHoursThreshold );
    }

    @Override
    PaymentSchedule getPaymentSchedule() {
        return new WeeklySchedule();
    }
}
