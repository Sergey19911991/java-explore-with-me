package ru.practicum.hit;

import ru.practicum.dto.DtoInletHit;
import ru.practicum.dto.HitDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final HitsRepository hitsRepository;

    @Override
    public HitDto creatHit(DtoInletHit hit) {
        Hit hit1 = new Hit();
        hit1.setApp(hit.getApp());
        hit1.setUri(hit.getUri());
        hit1.setIp(hit.getIp());
        hit1.setTimestamp(LocalDateTime.parse(hit.getTimestamp(), formatter));
        hitsRepository.save(hit1);
        HitDto hitDto = new HitDto();
        hitDto.setApp(hit.getApp());
        hitDto.setUri(hit.getUri());
        hitDto.setHits(hit.getHits());
        log.info("Сохранена информация о просмотре событий");
        return hitDto;
    }

    @Override
    public List<HitDto> getHits(LocalDateTime start, LocalDateTime end, String[] uri, boolean unique) {
        List<HitDto> hitDtos = new ArrayList<>();
        if (uri != null) {
            if (unique == false) {
                hitDtos = hitsRepository.getHitDto(start, end, uri);
            } else {
                hitDtos = hitsRepository.getHitsUnique(start, end, uri);
            }
        }
        if (uri == null) {
            if (unique == false) {
                hitDtos = hitsRepository.getHitsUriNull(start, end);
            } else {
                hitDtos = hitsRepository.getHitsUniqueUriNull(start, end);
            }
        }
        log.info("Информация о просмотре событий");
        return hitDtos;
    }
}
