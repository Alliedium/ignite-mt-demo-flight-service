package com.ignitesummit.flightservice.generators;

import java.nio.file.Paths;

public class PropertiesInstaller {

    public static void installIgniteProperties() {
        if (System.getProperty("IGNITE_HOME") == null) {
            System.setProperty("IGNITE_HOME", Paths.get("ignite").toAbsolutePath().toString());
        }
    }
}
