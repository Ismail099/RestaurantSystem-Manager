package org.ibo.manager.api.mapper;

import org.ibo.manager.api.model.TypeDTO;
import org.ibo.manager.models.Type;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class TypeMapperTest {
    private TypeMapper typeMapper;

    private final String typeName = "FOOD";

    @Autowired
    public TypeMapperTest( TypeMapper typeMapper ) {
        this.typeMapper = typeMapper;
    }

    @Test
    void testTypeToTypeDTO() {
        Type type = Type.type( typeName );

        TypeDTO typeDTO = typeMapper.typeToTypeDTO( type );

        assertEquals( typeName, typeDTO.getName() );
    }
}