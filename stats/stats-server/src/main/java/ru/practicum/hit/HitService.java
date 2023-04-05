package ru.practicum.hit;

import ru.practicum.dto.HitDto;
import ru.practicum.dto.DtoInletHit;

import java.time.LocalDateTime;
import java.util.List;

public interface HitService {
    HitDto creatHit(DtoInletHit hit);

    List<HitDto> getHits(LocalDateTime start, LocalDateTime end, String[] uri, boolean unique);
}
