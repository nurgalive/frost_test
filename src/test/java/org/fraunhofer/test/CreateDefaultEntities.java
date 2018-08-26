package org.fraunhofer.test;

import de.fraunhofer.iosb.ilt.sta.ServiceFailureException;
import de.fraunhofer.iosb.ilt.sta.jackson.IntervalSerializer;
import de.fraunhofer.iosb.ilt.sta.model.Datastream;
import de.fraunhofer.iosb.ilt.sta.model.Location;
import de.fraunhofer.iosb.ilt.sta.model.MultiDatastream;
import de.fraunhofer.iosb.ilt.sta.model.Observation;
import de.fraunhofer.iosb.ilt.sta.model.ObservedProperty;
import de.fraunhofer.iosb.ilt.sta.model.Sensor;
import de.fraunhofer.iosb.ilt.sta.model.Thing;
import de.fraunhofer.iosb.ilt.sta.model.TimeObject;
import de.fraunhofer.iosb.ilt.sta.model.ext.UnitOfMeasurement;
import de.fraunhofer.iosb.ilt.sta.service.SensorThingsService;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import org.geojson.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.extra.Interval;


public class CreateDefaultEntities {


    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateDefaultEntities.class);
    private final SensorThingsService service;

    /**
     * @param args the command line arguments
     * @throws ServiceFailureException when there is an error.
     * @throws java.net.URISyntaxException
     * @throws java.net.MalformedURLException
     */
    
    Random random = new Random();
    
    public static void main(String[] args) throws ServiceFailureException, URISyntaxException, MalformedURLException {
        LOGGER.info("Creating test entities in {}", Constants.BASE_URL);
        CreateDefaultEntities tester = new CreateDefaultEntities();  //creating objects
        tester.createEntities();
    }

    public CreateDefaultEntities() throws URISyntaxException, MalformedURLException {
        service = Constants.createService(); //creating main library method
    }

    private void createEntities() throws ServiceFailureException, URISyntaxException {
        Thing thing = new Thing();
        thing.setName("thing name 1");
        thing.setDescription("thing 1");
        {
            Map<String, Object> properties = new HashMap<String, Object>();
            properties.put("reference", "firstThing");
            thing.setProperties(properties);
        }

        {
            Location location = new Location();
            location.setName("location name 1");
            location.setDescription("location 1");
            location.setLocation(new Point(-117.05, 51.05));
            location.setEncodingType("application/vnd.geo+json");
            Map<String, Object> properties = new HashMap<String, Object>();
            properties.put("reference", "firstLocation");
//            location.setProperties(properties);
            thing.getLocations().add(location);
        }

        {
            UnitOfMeasurement um1 = new UnitOfMeasurement("Lumen", "lm", "http://www.qudt.org/qudt/owl/1.0.0/unit/Instances.html/Lumen");
            Datastream ds = new Datastream("datastream name 1", "datastream 1", "http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_Measurement", um1);
            {
                Map<String, Object> properties = new HashMap<String, Object>();
                properties.put("reference", "firstDatastream");
//                ds.setProperties(properties);
            }
            {
                ObservedProperty op = new ObservedProperty("Luminous Flux", new URI("http://www.qudt.org/qudt/owl/1.0.0/quantity/Instances.html/LuminousFlux"), "observedProperty 1");
                Map<String, Object> properties = new HashMap<String, Object>();
                properties.put("reference", "firstObservedProperty");
//                op.setProperties(properties);
                ds.setObservedProperty(op);
            }
            {
                Sensor s = new Sensor("sensor name 1", "sensor 1", "application/pdf", "Light flux sensor");
                Map<String, Object> properties = new HashMap<String, Object>();
                properties.put("reference", "firstSensor");
//                s.setProperties(properties);
                ds.setSensor(s);
            }
            ds.getObservations().add(new Observation(41, ZonedDateTime.parse("2019-03-07T00:00:00Z")));
            ds.getObservations().add(new Observation(42, ZonedDateTime.parse("2019-03-08T00:00:00Z")));
            ds.getObservations().add(new Observation(43, ZonedDateTime.parse("2019-03-09T00:00:00Z")));
            ds.getObservations().add(new Observation(44, ZonedDateTime.parse("2019-03-10T00:00:00Z")));
            ds.getObservations().add(new Observation(45, ZonedDateTime.parse("2019-03-11T00:00:00Z")));
            ds.getObservations().add(new Observation(46, ZonedDateTime.parse("2019-03-12T00:00:00Z")));
            ds.getObservations().add(new Observation(random.nextInt(10), Interval.parse("2007-12-03T10:15:30Z/2007-12-04T10:15:30Z")));
            thing.getDatastreams().add(ds);
        }
        service.create(thing);
    }

}