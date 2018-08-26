package org.fraunhofer.test;

import de.fraunhofer.iosb.ilt.sta.ServiceFailureException;
import de.fraunhofer.iosb.ilt.sta.dao.ObservationDao;
import de.fraunhofer.iosb.ilt.sta.model.Datastream;
import de.fraunhofer.iosb.ilt.sta.model.Location;
import de.fraunhofer.iosb.ilt.sta.model.Observation;
import de.fraunhofer.iosb.ilt.sta.model.ObservedProperty;
import de.fraunhofer.iosb.ilt.sta.model.Sensor;
import de.fraunhofer.iosb.ilt.sta.model.Thing;
import de.fraunhofer.iosb.ilt.sta.model.ext.EntityList;
import de.fraunhofer.iosb.ilt.sta.model.ext.UnitOfMeasurement;
import de.fraunhofer.iosb.ilt.sta.service.SensorThingsService;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.geojson.LineString;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.geojson.Polygon;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.extra.Interval;

import org.joda.time.DateTime;
import org.joda.time.Duration;

public class SelectData {

    private static final Logger LOGGER = LoggerFactory.getLogger(SelectData.class.getName());
    private SensorThingsService service;
   // private List<Observation> observations = new ArrayList<Observation>();


    /**
     * @param args the command line arguments
     * @throws ServiceFailureException when there is an error.
     * @throws java.net.URISyntaxException
     * @throws java.net.MalformedURLException
     */
    public static void main(String[] args) throws ServiceFailureException, URISyntaxException, MalformedURLException {
        LOGGER.info("Connecting to {}", Constants.BASE_URL);
        SelectData tester = new SelectData();
        tester.createEntities();
    }

    public SelectData() throws MalformedURLException, URISyntaxException {
        service = Constants.createService();
    }


    private void createEntities() throws ServiceFailureException, URISyntaxException, MalformedURLException {
    	//variable for time evaluation
    	long startTime = System.currentTimeMillis();
    	long p = 0;
    	
//    	Thing ch = service.things().find(3);
//    	LOGGER.info("Found thing {}", ch);
//    	
//
//    	LOGGER.info("Check {}", service.observations().find(1));
//    	//service.things().find(4);
//    	
//    	//output of Things
//    	
//    	EntityList<Thing> things = service.things()
//                .query()
//                .count()
//                .orderBy("id")
//                .select("name","id","description")
//                //.filter("")
//                //.skip(5)
//                .top(100)
//                .list();
//    	
//    	
//
//		for (Thing thing1 : things) {
//		LOGGER.info("Thing ID: {} / Thing description: {}", thing1.getId(), thing1.getDescription());
//		}
    	
    	
    	
		//output of Observations
		
    	
    	EntityList<Observation> observations = service.observations()
    			.query()
    			//.count()
    			.orderBy("id")
    			.filter("id lt 5")
    			.top(1000)
    			.list();
    	
    	for (Observation observation : observations) {
    		LOGGER.info("Observation ID: {} / Observation result: {}", observation.getId(), observation.getResult());
    		p++;
    	}
    // output of time evaluation	
    long stopTime = System.currentTimeMillis();
    long elapsedTime = stopTime - startTime;
    LOGGER.info("Elapsed time: {}ms / Count: {}", elapsedTime, p);

    } 

}