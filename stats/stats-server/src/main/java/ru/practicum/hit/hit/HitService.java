package ru.practicum.hit.hit;

import dto.HitDto;

import java.util.List;

public interface HitService {
    Hit creatHit(Hit hit);

    List<HitDto> getHits(String start, String end, String[] uri, boolean unique);
}
