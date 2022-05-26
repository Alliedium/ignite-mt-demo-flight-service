package com.ignitesummit.flightservice.entities;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Flight {
    private final long id;
    private final List<Passenger> passengerList;
    private final Airport airport;
}
