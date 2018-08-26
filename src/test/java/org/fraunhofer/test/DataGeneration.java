package org.fraunhofer.test;

import de.fraunhofer.iosb.ilt.sta.ServiceFailureException;
import de.fraunhofer.iosb.ilt.sta.dao.DatastreamDao;
import de.fraunhofer.iosb.ilt.sta.dao.ObservationDao;
import de.fraunhofer.iosb.ilt.sta.model.Datastream;
import de.fraunhofer.iosb.ilt.sta.model.Location;
import de.fraunhofer.iosb.ilt.sta.model.Observation;
import de.fraunhofer.iosb.ilt.sta.model.ObservedProperty;
import de.fraunhofer.iosb.ilt.sta.model.Sensor;
import de.fraunhofer.iosb.ilt.sta.model.Thing;
import de.fraunhofer.iosb.ilt.sta.model.ext.DataArrayDocument;
import de.fraunhofer.iosb.ilt.sta.model.ext.DataArrayValue;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.fraunhofer.test.CreateEntities.obsCreator;
import org.geojson.LineString;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.geojson.Polygon;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.extra.Interval;
import java.util.Random;

public class DataGeneration {
	
    /**
     * The number of observations that will be created.
     */
    private static final int OBSERVATION_COUNT = 10;

    private static final Logger LOGGER = LoggerFactory.getLogger(DataGeneration.class.getName());
    private SensorThingsService service;

    static Random random = new Random();

    /**
     * @param args the command line arguments
     * @throws ServiceFailureException when there is an error.
     * @throws java.net.URISyntaxException
     * @throws java.net.MalformedURLException
     */
    public static void main(String[] args) throws ServiceFailureException, URISyntaxException, MalformedURLException {
        LOGGER.info("Creating test entities in {}", Constants.BASE_URL);
        DataGeneration tester = new DataGeneration();
        tester.createEntities();
    }

    public DataGeneration() throws MalformedURLException, URISyntaxException {
        service = Constants.createService();
    }


    private void createEntities() throws ServiceFailureException, URISyntaxException, MalformedURLException {
    	
    	Datastream datastream1 = service.datastreams().find(12);

        int totalCount = OBSERVATION_COUNT;
        int perTask = 10000;

        long startTime = Calendar.getInstance().getTimeInMillis();
        Duration delta = Duration.standardMinutes(1);
        DateTime dtStart = DateTime.now().minus(delta.multipliedBy(totalCount));

        int start = 0;
        while (start < totalCount) {
            if (start + perTask >= totalCount) {
                perTask = totalCount - start;
            }
            obsCreator obsCreator = new obsCreator(
                    new SensorThingsService(new URL(Constants.BASE_URL)),
                    datastream1, start, perTask, dtStart, delta);
            LOGGER.info("Submitted task for {} observations starting at {}.", perTask, start);
            start += perTask;
        }
        
        long endTime = Calendar.getInstance().getTimeInMillis();
        long duration = endTime - startTime;
        double secs = duration / 1000.0;
        LOGGER.info("Created {} obs in {}ms, {}/s.", totalCount, duration, totalCount / secs);
    }

    private final static class obsCreator {

        private final SensorThingsService service;
        private final Datastream datastream;
        private final int start;
        private final int count;
        private final DateTime startTime;
        private final Duration deltaPerObs;

        public obsCreator(SensorThingsService service, Datastream datastream, int start, int count, DateTime startTime, Duration deltaPerObs) {
            this.service = service;
            this.datastream = datastream;
            this.start = start;
            this.count = count;
            this.startTime = startTime;
            this.deltaPerObs = deltaPerObs;

            int end = start + count;
            int i = 0;
            LOGGER.info("Creating {} observations from {} to {}.", count, start, end);
            try {
                for (i = start; i < end; i++) {
                    Observation o = new Observation(random.nextInt(10), datastream);
                    long millis = startTime.plus(deltaPerObs.multipliedBy(i)).getMillis();
                    o.setResultTime(ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault()));
                    
      
                    service.create(o);
                }
            } catch (ServiceFailureException ex) {
                LOGGER.error("Failed to create observation {}: {}", i);
                LOGGER.error("", ex);

            }
            LOGGER.info("Done creating {} observations from {} to {}.", count, start, end);
        }
    }
}