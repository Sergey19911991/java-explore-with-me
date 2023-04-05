package ru.practicum.hit;

import ru.practicum.dto.HitDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface HitsRepository extends JpaRepository<Hit, Long> {
    @Query("select new ru.practicum.dto.HitDto( h.app,h.uri, count(h.ip)) " +
            "from Hit as h " +
            "WHERE h.timestamp > ?1 " +
            "AND h.timestamp<?2 " +
            "AND h.uri IN (?3) " +
            "Group by h.app, h.uri " +
            "order by count(h.ip) desc")
    List<HitDto> getHitDto(LocalDateTime start, LocalDateTime end, String[] uri);


    @Query("select new ru.practicum.dto.HitDto(h.app,h.uri, count(distinct h.ip)) " +
            "from Hit as h " +
            "WHERE h.timestamp > ?1 " +
            "AND h.timestamp<?2 " +
            "AND h.uri IN (?3) " +
            "Group by h.app, h.uri " +
            "order by count(distinct h.ip) desc")
    List<HitDto> getHitsUnique(LocalDateTime start, LocalDateTime end, String[] uri);

    @Query("select new ru.practicum.dto.HitDto(h.app,h.uri, count(h.ip)) " +
            "from Hit as h " +
            "WHERE h.timestamp > ?1 " +
            "AND h.timestamp<?2 " +
            "Group by h.app, h.uri " +
            "order by count(h.ip) desc")
    List<HitDto> getHitsUriNull(LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.dto.HitDto(h.app,h.uri, count(distinct  h.ip)) " +
            "from Hit as h " +
            "WHERE h.timestamp > ?1 " +
            "AND h.timestamp<?2 " +
            "Group by h.app, h.uri " +
            "order by count(distinct  h.ip) desc")
    List<HitDto> getHitsUniqueUriNull(LocalDateTime start, LocalDateTime end);


}
