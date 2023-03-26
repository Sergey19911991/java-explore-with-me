package ru.practicum.main.categorie;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;


}
