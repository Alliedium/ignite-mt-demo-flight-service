package com.ignitesummit.flightservice.constants;

import com.ignitesummit.flightservice.entities.Airport;

import java.util.Random;

public enum Airports {

    KIV(new Airport("KIV", 28.93, 46.93)),
    MUC(new Airport("MUC", 11.77, 48.35)),
    CDG(new Airport("CDG", 2.55, 49.00));

    private static final Random random = new Random();
    private final Airport airport;

    Airports(Airport airport) {
        this.airport = airport;
    }

    public static Airport getRandomAirport() {
        return values()[random.nextInt(values().length)].airport;
    }
}
