package br.com.iagolaguna.senior.challange.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class IntegrationService {
    @Value("${csv.city.path}")
    private String csvCityPath;


    @PostConstruct
    void postConstruct(){
        File csvPathDirectory = new File(csvCityPath);
        System.out.println(csvCityPath);
        if (!csvPathDirectory.exists()){
            boolean created = csvPathDirectory.mkdirs();
            if(!created){
                throw new IllegalStateException(String.format("can't create csv city path %s",csvCityPath));
            }
        }
    }

    public File saveMultipartFile(MultipartFile multipartFile) throws IOException {
        File file = new File(new File(csvCityPath),"cities.csv");
        Files.copy(multipartFile.getInputStream(),file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return file;
    }


}
