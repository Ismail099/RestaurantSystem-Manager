package org.ibo.manager.api.mapper;

import org.ibo.manager.models.Table;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.ibo.manager.api.model.TableDTO;

@Mapper(componentModel = "spring")
@Component
public interface TableMapper {

    TableDTO tableToTableDTO( Table table );
}
