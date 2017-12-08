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

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<DefaultResponse> saveCity(@RequestBody City city) {
        cityService.saveNewCity(city);
        return ResponseEntity.ok(new DefaultResponse(HttpStatus.CREATED,"new city saved with success."));
    }

    @RequestMapping(value = "/capitals", method = RequestMethod.GET)
    public ResponseEntity<List<City>> getCapitals() {
        return ResponseEntity.ok(cityService.getCapitals());
    }

    @RequestMapping(value = "/{cityId}", method = RequestMethod.GET)
    public ResponseEntity<City> getCapitals(@PathVariable("cityId") Integer cityId) {
        City city = cityService.findById(cityId);
        if (city == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(city);
    }

    @RequestMapping(value = "/state/{uf}", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getCapitals(@PathVariable("uf") String uf) {
        List<String> cities = cityService.findByUf(uf);
        if (cities == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cities);
    }

    @RequestMapping(value = "/cities-for-state", method = RequestMethod.GET)
    public List<CityByStateDto> findQuantityOfCityForState() {
        return cityService.findQuantityOfCityForState();
    }

    @RequestMapping(value = "/size", method = RequestMethod.GET)
    public Long getQuantity() {
        return cityService.quantityOfCities();
    }

    @RequestMapping(value = "/{column}")
    public ResponseEntity<Long> getQuantityOfRecordsByColumn(@PathVariable("column") String column) {
        if (StringUtils.isEmpty(column)) {
            return ResponseEntity.badRequest().body(null);
        }
//        return ResponseEntity.ok(cityService.getSizeRecordsByColumn(column));
        throw new NotImplementedException();
    }
}
