package br.com.iagolaguna.senior.challange.api;


import br.com.iagolaguna.senior.challange.pojo.DefaultResponse;
import br.com.iagolaguna.senior.challange.service.CityService;
import br.com.iagolaguna.senior.challange.service.IntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class IntegrationAPI {

    @Autowired
    private IntegrationService integrationService;

    @Autowired
    private CityService cityService;
    // requisito 1
    @RequestMapping(value = "/import-cities", method = RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity importCities(@RequestParam("file") MultipartFile file) {
        try {
            File fileSaved = integrationService.saveMultipartFile(file);
            cityService.parseCsvFileToData(fileSaved);
            return ResponseEntity.ok(new DefaultResponse(HttpStatus.OK,"cities imported with success."));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
