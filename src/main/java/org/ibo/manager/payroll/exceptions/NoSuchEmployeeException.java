package org.ibo.manager.payroll.exceptions;

public class NoSuchEmployeeException extends Throwable {
    private final Long empId;

    public NoSuchEmployeeException( Long empId ) {
        this.empId = empId;
    }

    public NoSuchEmployeeException( String message, Long empId ) {
        super(message);
        this.empId = empId;
    }

    public Long getEmployeeId() {
        return empId;
    }
}
