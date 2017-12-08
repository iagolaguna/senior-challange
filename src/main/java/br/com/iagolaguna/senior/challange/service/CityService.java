package br.com.iagolaguna.senior.challange.service;

import br.com.iagolaguna.senior.challange.db.City;
import br.com.iagolaguna.senior.challange.db.CityRepository;
import br.com.iagolaguna.senior.challange.db.State;
import br.com.iagolaguna.senior.challange.db.StateRepository;
import br.com.iagolaguna.senior.challange.pojo.CityByStateDto;
import br.com.iagolaguna.senior.challange.pojo.GrahamPoint;
import br.com.iagolaguna.senior.challange.pojo.GrahamScan;
import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {
    private final static Logger LOG = LoggerFactory.getLogger(CityService.class);


    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private StateRepository stateRepository;

    public void parseCsvFileToData(File file) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(file)), ',')) {

            final Map<String, Integer> header = new HashMap<>();
            int i = 0;
            for (String column : reader.readNext()) {
                header.put(column, i++);
            }

            reader.forEach(line -> saveCity(header, line));
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage(), e);
        }

    }

    private void saveCity(Map<String, Integer> header, String[] line) {
        String uf = line[header.get("uf")];
        State state = new State(uf);
        stateRepository.save(state);

        int ibgeId = Integer.parseInt(line[header.get("ibge_id")]);

        String name = line[header.get("name")];
        String noAccents = line[header.get("no_accents")];
        String alternativeNames = line[header.get("alternative_names")];
        String microregion = line[header.get("microregion")];
        String mesoregion = line[header.get("mesoregion")];
        boolean capital = "TRUE".equals(line[header.get("capital")].toUpperCase());
        double lon = Double.parseDouble(line[header.get("lon")]);
        double lat = Double.parseDouble(line[header.get("lat")]);

        City city = new City(ibgeId, name, noAccents, alternativeNames, microregion, mesoregion, capital, lon, lat, state);
        cityRepository.save(city);
    }

    public void deleteCityById(Integer cityId) {
        cityRepository.delete(cityId);
    }

    public List<City> getCapitals() {
        return cityRepository.findByCapitalOrderByNoAccents(true);
    }

    public City findById(Integer cityId) {
        return cityRepository.findOne(cityId);
    }

    public List<String> findByUf(String uf) {
        State state = stateRepository.findOne(uf);
        return cityRepository.findNameByState(state);
    }

    public List<CityByStateDto> findQuantityOfCityForState() {
        return cityRepository.findQuantityOfCitiesForState();
    }

    public Long quantityOfCities() {
        return cityRepository.count();
    }

    public void saveNewCity(City city) {
        cityRepository.save(city);
    }

    public List<CityByStateDto> majorAndMirrorStatesWithCities() {
        CityByStateDto max = cityRepository.findQuantityOfCitiesForState().stream()
                .max(Comparator.comparingLong(CityByStateDto::getQuantity)).get();

        CityByStateDto min = cityRepository.findQuantityOfCitiesForState().stream()
                .min(Comparator.comparingLong(CityByStateDto::getQuantity)).get();

        return new ArrayList<CityByStateDto>() {{
            add(max);
            add(min);
        }};
    }

    public List<City> getExtremeCities() {
        GrahamPoint gpa = null;
        GrahamPoint gpb = null;
        double majorDistance = 0;
        List<City> cities = cityRepository.findAll();
        List<GrahamPoint> points = cities.stream()
                .map(city -> new GrahamPoint(city.getLon(), city.getLat()))
                .collect(Collectors.toList());
        List<GrahamPoint> convexPoints = GrahamScan.getConvexHull(points);
        List<GrahamPoint> secondPoints = new ArrayList<GrahamPoint>(convexPoints);
        for (GrahamPoint point : convexPoints) {
            for (GrahamPoint secondPoint : secondPoints) {
                double distance =distanceTo(point.getX(), point.getY(), secondPoint.getX(), secondPoint.getY());
                if (distance > majorDistance) {
                    majorDistance = distance;
                    gpa = point;
                    gpb = secondPoint;
                }
            }
        }


        GrahamPoint finalGpa = gpa;
        GrahamPoint finalGpb = gpb;

        City a = cities.stream()
                .filter(city -> city.getLat().equals(finalGpa.getY()) && city.getLon().equals(finalGpa.getX()))
                .findFirst().get();

        City b = cities.stream().
                filter(city -> city.getLat().equals(finalGpb.getY()) && city.getLon().equals(finalGpb.getX()))
                .findFirst().get();
        return new ArrayList<City>() {{
            add(a);
            add(b);
        }};
    }

    public double distanceTo(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

}
