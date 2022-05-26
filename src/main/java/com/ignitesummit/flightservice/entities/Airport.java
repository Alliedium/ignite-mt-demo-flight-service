package com.ignitesummit.flightservice.entities;

import lombok.*;

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class Airport {
    private final String name;
    private final double latitude;
    private final double longitude;
}
