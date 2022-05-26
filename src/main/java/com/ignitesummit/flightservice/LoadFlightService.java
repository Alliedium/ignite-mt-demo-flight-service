package com.ignitesummit.flightservice;

import com.ignitesummit.flightservice.entities.Flight;
import com.ignitesummit.flightservice.entities.FlightProfit;
import com.ignitesummit.flightservice.generators.DataGenerator;
import com.ignitesummit.flightservice.generators.PropertiesInstaller;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterState;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;

import java.net.URL;

public class LoadFlightService {

    public static void main(String[] args) {
        PropertiesInstaller.installIgniteProperties();

        URL clientXmlConfig = LoadFlightService.class.getClassLoader().getResource("client.xml");
        IgniteConfiguration configuration = Ignition.loadSpringBean(clientXmlConfig, "default-ignite.cfg");
        Ignite ignite = Ignition.start(configuration);
        ignite.cluster().state(ClusterState.ACTIVE);

        CacheConfiguration<Integer, Flight> flightsCacheConfig = new CacheConfiguration<>();
        flightsCacheConfig.setName("flightsCache");
        IgniteCache<Integer, Flight> flightsCache = ignite.createCache(flightsCacheConfig);

        CacheConfiguration<Integer, FlightProfit> flightsProfitConfig = new CacheConfiguration<>();
        flightsProfitConfig.setName("flightsProfitCache");
        IgniteCache<Integer, FlightProfit> flightsProfitCache = ignite.createCache(flightsProfitConfig);

        for (int i = 0; i < 100; i++) {
            Flight flight = DataGenerator.generateFlight();
            flightsCache.put(i, flight);
            flightsProfitCache.put(i, DataGenerator.generateFlightProfit(flight));
        }

        ignite.close();
    }

}
