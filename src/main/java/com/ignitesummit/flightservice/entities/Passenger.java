package com.ignitesummit.flightservice.entities;

import lombok.Data;

@Data
public class Passenger {
    private final String name;
    private final int age;
    private final Passport passport;
}
