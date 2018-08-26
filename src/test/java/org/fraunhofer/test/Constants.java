package org.fraunhofer.test;

import de.fraunhofer.iosb.ilt.sta.service.SensorThingsService;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;




public class Constants {

    public static String BASE_URL = "http://localhost:8080/FROST-Server/v1.0";


    public static SensorThingsService createService() throws MalformedURLException, URISyntaxException {
        return createService(BASE_URL);
    }

    public static SensorThingsService createService(String serviceUrl) throws MalformedURLException, URISyntaxException {
        URL url = new URL(serviceUrl);
        return createService(url);
    }

    public static SensorThingsService createService(URL serviceUrl) throws MalformedURLException, URISyntaxException {
        SensorThingsService service = new SensorThingsService(serviceUrl);
        return service;
    }
}