package com.ignitesummit.flightservice.entities;

import lombok.Data;

@Data
public class FlightProfit {
    private final long id;
    private final int expense;
    private final int income;
    private final int passengersCount;
}
