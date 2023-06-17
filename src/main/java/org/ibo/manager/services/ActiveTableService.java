package org.ibo.manager.services;

import org.ibo.manager.api.model.TableDTO;
import org.ibo.manager.models.Customer;
import org.ibo.manager.models.Table;

import java.util.List;

/**
 * Used to manage requests for active {@link Table}s and deliver them to the REST API
 */
public interface ActiveTableService {
    /**
     * @return a List of active {@link TableDTO} which are {@link Table}s that are currently occupied by {@link Customer}s
     */
    List<TableDTO> getActiveTables();

    /**
     * @param id Active {@link Table} id
     * @return an active {@link TableDTO} which is a {@link Table} that is currently occupied by a {@link Customer}
     */
    TableDTO getActiveTableById( Long id );
}
