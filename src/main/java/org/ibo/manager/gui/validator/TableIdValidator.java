package org.ibo.manager.gui.validator;

import org.ibo.manager.repositories.Database;

public class TableIdValidator extends IdValidator {
    @Override
    boolean isUnique( Long id ) {
        return !Database.getTablesId().contains( id );
    }
}
