package ru.practicum.hit.hit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface HitsRepository extends JpaRepository<Hit, Integer> {


    @Query(value = "select *  " +
            "from hit as h " +
            "WHERE  CAST(h.timestamp AS timestamp) > ?1 " +
            "AND CAST(h.timestamp AS timestamp)<?2 " +
            "AND h.uri IN (?3)",
            nativeQuery = true)
    List<Hit> getHits(LocalDateTime start, LocalDateTime end, String[] uri);

    @Query(value = "select DISTINCT *" +
            "from hit as h " +
            "WHERE  CAST(h.timestamp AS timestamp) > ?1 " +
            "AND CAST(h.timestamp AS timestamp)<?2 " +
            "AND h.uri IN (?3)",
            nativeQuery = true)
    List<Hit> getHitsUnique(LocalDateTime start, LocalDateTime end, String[] uri);

    @Query(value = "select *  " +
            "from hit as h " +
            "WHERE  CAST(h.timestamp AS timestamp) > ?1 " +
            "AND CAST(h.timestamp AS timestamp)<?2 ",
          nativeQuery = true)
    List<Hit> getHitsUriNull(LocalDateTime start, LocalDateTime end);

    @Query(value = "select DISTINCT * " +
            "from hit as h " +
            "WHERE  CAST(h.timestamp AS timestamp) > ?1 " +
            "AND CAST(h.timestamp AS timestamp)<?2 ",
            nativeQuery = true)
    List<Hit> getHitsUniqueUriNull(LocalDateTime start, LocalDateTime end);
}
