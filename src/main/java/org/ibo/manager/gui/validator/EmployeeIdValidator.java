package org.ibo.manager.gui.validator;

import org.ibo.manager.repositories.Database;

public class EmployeeIdValidator extends IdValidator {
    @Override
    boolean isUnique( Long id ) {
        return !Database.getEmployeesId().contains( id );
    }
}
