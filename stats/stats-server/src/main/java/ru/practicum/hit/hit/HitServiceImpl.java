package ru.practicum.hit.hit;

import dto.DtoInletHit;
import dto.HitDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {

    private final HitsRepository hitsRepository;

    @Override
    public HitDto creatHit(DtoInletHit hit) {
        Hit hit1 = new Hit();
        hit1.setApp(hit.getApp());
        hit1.setUri(hit.getUri());
        hit1.setIp(hit.getIp());
        hit1.setTimestamp(hit.getTimestamp());
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
        List<Hit> hits = new ArrayList<>();
        if (uri != null) {
            if (unique == false) {
                hits = hitsRepository.getHits(start, end, uri);
            } else {
                hits = hitsRepository.getHitsUnique(start, end, uri);
            }
        }
        if (uri == null) {
            if (unique == false) {
                hits = hitsRepository.getHitsUriNull(start, end);
            } else {
                hits = hitsRepository.getHitsUniqueUriNull(start, end);
            }
        }
        List<HitDto> hitsDto = new ArrayList<>();
        if (uri != null) {
            for (int i = 0; i <= uri.length - 1; i++) {
                HitDto hitDto = new HitDto();
                hitDto.setUri(uri[i]);
                int k = 0;
                for (int j = 0; j <= hits.size() - 1; j++) {
                    if (hits.get(j).getUri().equals(uri[i])) {
                        k++;
                    }
                }
                hitDto.setApp("ewm-main-service");
                hitDto.setHits(k);
                hitsDto.add(hitDto);
            }
        }
        if (uri == null) {
            Map<String, Integer> uris = new HashMap<>();
            for (Hit hit : hits) {
                if (uris.keySet().contains(hit.getUri())) {
                    uris.put(hit.getUri(), uris.get(hit.getUri()) + 1);
                } else {
                    uris.put(hit.getUri(), 1);
                }
            }
            for (String key : uris.keySet()) {
                HitDto hitDto = new HitDto();
                hitDto.setHits(uris.get(key));
                hitDto.setUri(key);
                hitDto.setApp("ewm-main-service");
                hitsDto.add(hitDto);
            }
        }

        Collections.sort(hitsDto, new Comparator<HitDto>() {
            @Override
            public int compare(HitDto o1, HitDto o2) {
                return (-1) * o1.getHits() + o2.getHits();
            }
        });
        log.info("Информация о просмотре событий");
        return hitsDto;
    }
}
