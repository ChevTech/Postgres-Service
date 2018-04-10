package com.chevtech;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

import static com.chevtech.Utils.printSQLException;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@RestController
public class PostgresController {
    private final Postgres pg = new Postgres();

    @RequestMapping(method=POST, value="/city")
    public ResponseEntity<?> insertCity(@RequestBody City input) {
        pg.insertCity(input);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method=PUT, value="/city")
    public ResponseEntity<?> updateCityLocation(@RequestBody City input) {
        pg.updateCityLocation(input);
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @RequestMapping(method=GET, value="/city/{cityName}")
    public City getCity(@PathVariable String cityName) {
        City city = pg.getCity(cityName);

        if(city == null){
            throw new CityNotFoundException(cityName);
        }

        return city;
    }

    @RequestMapping(method=GET, value="/weather/{cityName}")
    public ArrayList<CityWeather> getCityWeather(@PathVariable String cityName) {
        ArrayList<CityWeather> rows = new ArrayList<CityWeather>();

        try {
            rows.addAll(pg.getCityWeather(cityName));
        } catch (SQLException ex){
            printSQLException(ex);
        }

        return rows;
    }

    @RequestMapping(method=GET, value="/weather")
    public ArrayList<CityWeather> getAllWeather(){
        ArrayList<CityWeather> cityWeather = new ArrayList<CityWeather>();

        try {
            cityWeather.addAll(pg.getAllWeather());
        } catch (SQLException ex){
            printSQLException(ex);
        }

        return cityWeather;
    }

    @RequestMapping(method=POST, value="/weather")
    public ResponseEntity<?> createCityWeather(@RequestBody CityWeather input) {
        try {
            pg.insertWeather(input);
        } catch (SQLException ex){
            printSQLException(ex);
        }

        return ResponseEntity.ok().build();
    }

    @RequestMapping(method=POST, value="/weathers")
    public ResponseEntity<?> createManyCityWeather(@RequestBody ArrayList<CityWeather> inputs){
        try{
            pg.insertManyWeather(inputs);
        } catch (SQLException ex){
            printSQLException(ex);
        }

        return ResponseEntity.ok().build();
    }

    @RequestMapping(method=DELETE, value="/weather/{cityName}")
    public ResponseEntity<?> deleteCityWeather(@PathVariable String cityName){
        try {
            pg.deleteCityWeather(cityName);
        } catch (SQLException ex){
            printSQLException(ex);
        }

        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    class CityNotFoundException extends RuntimeException {
        public CityNotFoundException (String city){
            super("Could not find city '" + city + "'.");
        }
    }
}
