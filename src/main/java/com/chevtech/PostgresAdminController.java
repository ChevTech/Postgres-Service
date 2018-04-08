package com.chevtech;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

import static com.chevtech.Utils.printSQLException;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class PostgresAdminController {
    private final PostgresAdmin pgAdmin = new PostgresAdmin();

    @RequestMapping(method=POST, value="/admin/createWeatherTable")
    public ResponseEntity createWeatherTable(){
        try {
            pgAdmin.createWeatherTable();
        } catch (SQLException ex){
            printSQLException(ex);
        }

        return ResponseEntity.ok().build();
    }

    @RequestMapping(method=POST, value="/admin/createCityTable")
    public ResponseEntity createCityTable(){
        try {
            pgAdmin.createCityTable();
        } catch (SQLException ex){
            printSQLException(ex);
        }

        return ResponseEntity.ok().build();
    }

    @RequestMapping(method=DELETE, value="/admin/drop/{table}")
    public ResponseEntity dropTable(@PathVariable String table){
        try {
            pgAdmin.dropTable(table);
        } catch (SQLException ex){
            printSQLException(ex);
        }

        return ResponseEntity.ok().build();
    }
}
