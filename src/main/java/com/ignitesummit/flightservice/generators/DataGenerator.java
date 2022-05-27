package com.ignitesummit.flightservice.generators;

import com.github.javafaker.Faker;
import com.ignitesummit.flightservice.constants.Airports;
import com.ignitesummit.flightservice.entities.Flight;
import com.ignitesummit.flightservice.entities.FlightProfit;
import com.ignitesummit.flightservice.entities.Passenger;
import com.ignitesummit.flightservice.entities.Passport;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    private static final Faker faker = new Faker();

    public static Flight generateFlight() {
        return Flight.builder()
                .id(faker.random().nextInt(1_000_000))
                .airport(Airports.getRandomAirport())
                .passengerList(generatePassengers())
                .build();
    }

    public static FlightProfit generateFlightProfit(Flight flight) {
        return new FlightProfit(flight.getId(), faker.random().nextInt(100, 500), faker.random().nextInt(350, 600), flight.getPassengerList().size());
    }

    private static List<Passenger> generatePassengers() {
        List<Passenger> passengers = new ArrayList<>();
        int passengersCount = faker.random().nextInt(1, 600);
        for (int i = 0; i < passengersCount; i++) {
            passengers.add(generatePassenger());
        }
        return passengers;
    }

    private static Passenger generatePassenger() {
        return new Passenger(faker.name().fullName(), generateAge(), generatePassport());
    }

    private static int generateAge() {
        return faker.random().nextInt(20, 80);
    }

    private static Passport generatePassport() {
        return new Passport("B" + faker.random().nextInt(10_000, 100_000));
    }
}
