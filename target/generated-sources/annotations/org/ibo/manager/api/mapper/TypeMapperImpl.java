package org.ibo.manager.api.mapper;

import javax.annotation.processing.Generated;
import org.ibo.manager.api.model.TypeDTO;
import org.ibo.manager.models.Type;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-17T16:43:40+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class TypeMapperImpl implements TypeMapper {

    @Override
    public TypeDTO typeToTypeDTO(Type type) {
        if ( type == null ) {
            return null;
        }

        TypeDTO typeDTO = new TypeDTO();

        typeDTO.setName( type.getName() );

        return typeDTO;
    }
}
