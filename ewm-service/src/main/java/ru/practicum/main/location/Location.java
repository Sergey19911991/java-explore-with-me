package ru.practicum.main.location;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

//@Entity
//@Table(name = "location")
@Getter
@Setter
@Embeddable
public class Location {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id")
   // private int id;
    //@Column(name = "lat")
    private double lat;
    //@Column(name = "lon")
    private double lon;
}
