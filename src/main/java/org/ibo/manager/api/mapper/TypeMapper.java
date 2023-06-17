package org.ibo.manager.api.mapper;

import org.ibo.manager.models.Type;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.ibo.manager.api.model.TypeDTO;

@Mapper(componentModel = "spring")
@Component
public interface TypeMapper {
    TypeDTO typeToTypeDTO( Type type );

    default Type typeDTOToType( TypeDTO typeDTO ) {
        return typeDTO == null ? null : Type.type( typeDTO.getName() );
    }
}
