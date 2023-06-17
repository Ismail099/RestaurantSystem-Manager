package org.ibo.manager.services;

import org.ibo.manager.repositories.Database;
import org.springframework.stereotype.Service;
import org.ibo.manager.api.mapper.TableMapper;
import org.ibo.manager.api.model.TableDTO;
import org.ibo.manager.models.Reservation;
import org.ibo.manager.models.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActiveTableServiceImpl implements ActiveTableService {
    private final TableMapper tableMapper;

    public ActiveTableServiceImpl( TableMapper tableMapper ) {
        this.tableMapper = tableMapper;
    }

    @Override
    public List<TableDTO> getActiveTables() {
        List<TableDTO> tables = new ArrayList<>();

        Database.getCurrentReservations().stream().filter( Reservation::isActive )
                .forEach( reservation ->
                        tables.add( tableMapper.tableToTableDTO( reservation.getTable() ) )
                );

        return tables;
    }

    @Override
    public TableDTO getActiveTableById( Long id ) {
        Table table = Database.getTableById( id );

        if ( isCurrentTableReservationActive( table ) ) {
            return tableMapper.tableToTableDTO( table );
        }

        return null;
    }

    private boolean isCurrentTableReservationActive( Table table ) {
        return table.isReserved( LocalDate.now() ) && Database.getCurrentReservationByTableId( table.getId() ).isActive();
    }
}
