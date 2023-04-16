package ru.practicum.main.location;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class Location {
    private double lat;
    private double lon;
}
