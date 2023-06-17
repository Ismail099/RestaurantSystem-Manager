package org.ibo.manager.services;

import org.ibo.manager.api.mapper.TypeMapper;
import org.ibo.manager.api.model.TypeDTO;
import org.ibo.manager.models.Type;
import org.ibo.manager.repositories.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith( SpringExtension.class )
@SpringBootTest
class TypeServiceTest {
    private TypeService typeService;
    private TypeMapper typeMapper;

    @BeforeEach
    void setUpDatabase() {
        Database.deleteTypes();
        fillDatabase();
    }

    private void fillDatabase() {
        Type.type( "FOOD" );
        Type.type( "HOT" );
        Type.type( "SNACK" );
    }

    @Autowired
    public TypeServiceTest( TypeService typeService, TypeMapper typeMapper ) {
        this.typeService = typeService;
        this.typeMapper = typeMapper;
    }

    @Test
    void testGetTypes() {
        List<TypeDTO> types = typeService.getTypes();

        List<TypeDTO> expected = List.of(
                typeMapper.typeToTypeDTO( Type.type( "FOOD" ) ),
                typeMapper.typeToTypeDTO( Type.type( "HOT" ) ),
                typeMapper.typeToTypeDTO( Type.type( "SNACK" ) )
        );

        assertEquals( expected, types );
    }
}