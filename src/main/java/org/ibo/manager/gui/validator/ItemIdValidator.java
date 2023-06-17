package org.ibo.manager.gui.validator;

import org.ibo.manager.repositories.Database;

public class ItemIdValidator extends IdValidator {
    @Override
    boolean isUnique( Long id ) {
        return !Database.getItemsId().contains( id );
    }
}
