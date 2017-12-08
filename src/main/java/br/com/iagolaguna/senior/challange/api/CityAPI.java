package br.com.iagolaguna.senior.challange.api;

import br.com.iagolaguna.senior.challange.db.City;
import br.com.iagolaguna.senior.challange.pojo.CityByStateDto;
import br.com.iagolaguna.senior.challange.pojo.DefaultResponse;
import br.com.iagolaguna.senior.challange.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

@RestController
@RequestMapping(value = "/city")
public class CityAPI {

    @Autowired
    CityService cityService;

    // requisito 8
    @RequestMapping(value = "/{cityId}", method = RequestMethod.DELETE)
    public ResponseEntity<DefaultResponse> deleteCity(@PathVariable("cityId") Integer cityId) {
        if (cityId == null) {
            return ResponseEntity.badRequest().body(new DefaultResponse(HttpStatus.BAD_REQUEST, "can't receive a null id"));
        }
        try {
            cityService.deleteCityById(cityId);
            return ResponseEntity.ok(new DefaultResponse(HttpStatus.OK, "the city has been removed from database"));
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }

        return ResponseEntity.notFound().build();
    }

    // requisito 7
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<DefaultResponse> saveCity(@RequestBody City city) {
        cityService.saveNewCity(city);
        return ResponseEntity.ok(new DefaultResponse(HttpStatus.CREATED, "new city saved with success."));
    }

    // requisito 2
    @RequestMapping(value = "/capitals", method = RequestMethod.GET)
    public ResponseEntity<List<City>> getCapitals() {
        return ResponseEntity.ok(cityService.getCapitals());
    }

    // requisito 5
    @RequestMapping(value = "/{cityId}", method = RequestMethod.GET)
    public ResponseEntity<City> getCapitals(@PathVariable("cityId") Integer cityId) {
        City city = cityService.findById(cityId);
        if (city == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(city);
    }

    // requisito 6
    @RequestMapping(value = "/state/{uf}", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getCapitals(@PathVariable("uf") String uf) {
        List<String> cities = cityService.findByUf(uf);
        if (cities == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cities);
    }

    // requisito 4
    @RequestMapping(value = "/cities-for-state", method = RequestMethod.GET)
    public List<CityByStateDto> findQuantityOfCityForState() {
        return cityService.findQuantityOfCityForState();
    }

    // requisito 11
    @RequestMapping(value = "/size", method = RequestMethod.GET)
    public Long getQuantity() {
        return cityService.quantityOfCities();
    }

    // requisito 3
    @RequestMapping(value = "/max-and-mirror-state-with-cities", method = RequestMethod.GET)
    public ResponseEntity<List<CityByStateDto>> majorAndMirrorStatesWithCities() {
        return ResponseEntity.ok(cityService.majorAndMirrorStatesWithCities());
    }
    //TODO requisito 10
    @RequestMapping(value = "/{column}")
    public ResponseEntity<Long> getQuantityOfRecordsByColumn(@PathVariable("column") String column) {
        if (StringUtils.isEmpty(column)) {
            return ResponseEntity.badRequest().body(null);
        }
//        return ResponseEntity.ok(cityService.getSizeRecordsByColumn(column));
        throw new NotImplementedException();
    }

    //TODO requisito 9
    @RequestMapping(value = "/{column}/{filter}", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getValuesFromCsvColumn(@PathVariable("column") String column, @PathVariable("filter") String filter) {
        throw new NotImplementedException();
    }

    //TODO requisito 12
    @RequestMapping(value = "/extremes", method = RequestMethod.GET)
    public ResponseEntity<List<City>> getExtremeCities() {
        return ResponseEntity.ok(cityService.getExtremeCities());
    }

}
