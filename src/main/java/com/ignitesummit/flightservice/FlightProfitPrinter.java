package com.ignitesummit.flightservice;

import com.ignitesummit.flightservice.entities.FlightProfit;
import com.ignitesummit.flightservice.generators.PropertiesInstaller;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;

import java.net.URL;

public class FlightProfitPrinter {

    public static void main(String[] args) {
        PropertiesInstaller.installIgniteProperties();

        URL clientXmlConfig = LoadFlightService.class.getClassLoader().getResource("client.xml");
        IgniteConfiguration configuration = Ignition.loadSpringBean(clientXmlConfig, "default-ignite.cfg");
        Ignite ignite = Ignition.start(configuration);

        IgniteCache<Integer, FlightProfit> flightProfitCache = ignite.cache("flightsProfitCache");
        flightProfitCache.forEach(item -> {
            System.out.println(item.getValue());
        });
    }
}
