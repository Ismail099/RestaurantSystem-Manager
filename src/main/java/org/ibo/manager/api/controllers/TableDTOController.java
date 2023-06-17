package org.ibo.manager.api.controllers;

import org.ibo.manager.services.ActiveTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.ibo.manager.api.model.TableDTO;
import org.ibo.manager.api.model.TableListDTO;

@Controller
@RequestMapping("/api/tables")
public class TableDTOController {
    private final ActiveTableService tableService;

    @Autowired
    public TableDTOController( ActiveTableService tableService ) {
        this.tableService = tableService;
    }

    @GetMapping
    public ResponseEntity<TableListDTO> getActiveTables() {
        return new ResponseEntity<>( new TableListDTO( tableService.getActiveTables() ), HttpStatus.OK );
    }

    @GetMapping("/{id}")
    public  ResponseEntity<TableDTO> getActiveTable( @PathVariable Long id ) {
        TableDTO table =  tableService.getActiveTableById( id );
        return new ResponseEntity<>( table, table == null ? HttpStatus.NOT_FOUND : HttpStatus.OK );
    }
}
