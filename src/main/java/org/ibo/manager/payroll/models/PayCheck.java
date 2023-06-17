package org.ibo.manager.payroll.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

/**
 * A paycheck that is giving to an employee as a payment
 */
@Getter
@RequiredArgsConstructor
public class PayCheck {
    @NonNull
    private final LocalDate payDate;
    @NonNull
    private final Double amount;
    @NonNull
    private final String details;
    @NonNull
    private final Employee employee;
}
