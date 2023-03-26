package ru.practicum.main.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EvebtRequestUpdateStatusResult {
    private List<Request> confirmedRequests;
    private List<Request> rejectedRequests;
}
