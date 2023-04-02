package ru.practicum.main.requests;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "requests")
@Getter
@Setter
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "created")
    private String created;
    @Column(name = "event")
    private int event;
    @Column(name = "requester")
    private int requester;
    @Column(name = "status")
    private String status;
}
