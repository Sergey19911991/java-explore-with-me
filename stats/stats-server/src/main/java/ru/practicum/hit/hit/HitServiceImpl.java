package ru.practicum.hit.hit;

import dto.HitDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {

    private final HitsRepository hitsRepository;

    @Override
    public Hit creatHit(Hit hit) {
        return hitsRepository.save(hit);
    }

    @Override
    public List<HitDto> getHits(String start, String end, String[] uri, boolean unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(start, formatter);
        LocalDateTime localDateTime1 = LocalDateTime.parse(end, formatter);
        List<Hit> hits = new ArrayList<>();
        if (unique == false) {
            hits = hitsRepository.getHits(localDateTime, localDateTime1, uri);
        } else {
            hits = hitsRepository.getHitsUnique(localDateTime, localDateTime1, uri);
        }
        List<HitDto> hitsDto = new ArrayList<>();
        for (int i = 0; i <= uri.length - 1; i++) {
            HitDto hitDto = new HitDto();
            hitDto.setUri(uri[i]);
            int k = 0;
            for (int j = 0; j <= hits.size() - 1; j++) {
                if (hits.get(j).getUri().equals(uri[i])) {
                    k++;
                }
            }
            hitDto.setHits(k);
            hitsDto.add(hitDto);
        }
        Collections.sort(hitsDto, new Comparator<HitDto>() {
            @Override
            public int compare(HitDto o1, HitDto o2) {
                return (-1) * o1.getHits() + o2.getHits();
            }
        });
        return hitsDto;
    }
}
