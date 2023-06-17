package org.ibo.manager.api.controllers;

import org.ibo.manager.services.TypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.ibo.manager.api.model.TypeListDTO;

@Controller
@RequestMapping("/api/types")
public class TypeDTOController {
    private final TypeService typeService;

    public TypeDTOController( TypeService typeService ) {
        this.typeService = typeService;
    }

    @GetMapping
    public ResponseEntity<TypeListDTO> getTypes() {
        return new ResponseEntity<>( new TypeListDTO( typeService.getTypes() ), HttpStatus.OK );
    }
}
