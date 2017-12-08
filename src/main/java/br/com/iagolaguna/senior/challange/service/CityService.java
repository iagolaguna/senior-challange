package br.com.iagolaguna.senior.challange.service;

import br.com.iagolaguna.senior.challange.db.City;
import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class CityService {
    private final static Logger LOG = LoggerFactory.getLogger(CityService.class);

    public void parseCsvFileToData(File file) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(file)), ',')) {

            final Map<String, Integer> header = new HashMap<>();
            int i = 0;
            for (String column : reader.readNext()) {
                header.put(column, i++);
            }

            reader.forEach(line -> saveCity(header,line));
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage(), e);
        }

    }

    private void saveCity(Map<String, Integer> header, String[] line) {
        int idIbge = Integer.parseInt(line[header.get("ibge_id")]);
        String uf = line[header.get("uf")];
        String name = line[header.get("name")];
        boolean capital = "TRUE".equals(line[header.get("capital")].toUpperCase());
        double lon = Double.parseDouble(line[header.get("lon")]);
        double lat = Double.parseDouble(line[header.get("lat")]);
        String no_accents = line[header.get("no_accents")];
        String alternative_names = line[header.get("alternative_names")];
        String microregion = line[header.get("microregion")];
        String mesoregion = line[header.get("mesoregion")];
        new City();
    }
}
