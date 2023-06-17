package org.ibo.manager.services;

import org.ibo.manager.repositories.Database;
import org.springframework.stereotype.Service;
import org.ibo.manager.api.mapper.TypeMapper;
import org.ibo.manager.api.model.TypeDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeServiceImpl implements TypeService {
    private final TypeMapper typeMapper;

    public TypeServiceImpl( TypeMapper typeMapper ) {
        this.typeMapper = typeMapper;
    }

    @Override
    public List<TypeDTO> getTypes() {
        return Database.getTypes().stream().map( typeMapper::typeToTypeDTO ).collect( Collectors.toList());
    }
}
