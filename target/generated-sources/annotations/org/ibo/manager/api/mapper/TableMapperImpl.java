package org.ibo.manager.api.mapper;

import javax.annotation.processing.Generated;
import org.ibo.manager.api.model.TableDTO;
import org.ibo.manager.models.Table;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-17T16:43:40+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class TableMapperImpl implements TableMapper {

    @Override
    public TableDTO tableToTableDTO(Table table) {
        if ( table == null ) {
            return null;
        }

        TableDTO tableDTO = new TableDTO();

        tableDTO.setId( table.getId() );
        tableDTO.setMaxCapacity( table.getMaxCapacity() );
        tableDTO.setFee( table.getFee() );

        return tableDTO;
    }
}
