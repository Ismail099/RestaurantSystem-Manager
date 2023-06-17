package org.ibo.manager.gui.validator;

import org.ibo.manager.repositories.Database;

public class CustomerIdValidator extends IdValidator {
    @Override
    boolean isUnique( Long id ) {
        return !Database.getCustomersId().contains( id );
    }
}
