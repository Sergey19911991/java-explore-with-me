package ru.practicum.main.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EventsRepository extends JpaRepository<Event, Integer> {
    @Query(value = "select * " +
            "from events as e " +
            "WHERE e.id_initiator = ?1 " +
            "ORDER BY e.id ASC " +
            "LIMIT ?3 OFFSET ?2",
            nativeQuery = true)
    List<Event> getEventsUser(int id, int from, int size);

    @Query(value = "select * " +
            "from events as e " +
            "WHERE  CAST(e.event_date AS timestamp) > ?3 " +
            "AND CAST(e.event_date AS timestamp)<?4 " +
            "AND e.id_category IN (?5) " +
            "AND e.paid=?6 " +
            "AND (e.annotation LIKE ?7 OR e.description LIKE ?7) " +
            "ORDER BY e.event_date ASC " +
            "LIMIT ?2 OFFSET ?1",
            nativeQuery = true)
    List<Event> getEvent(int from, int size, LocalDateTime start, LocalDateTime end, int[] categories,
                         Boolean paid, String text);

    @Query(value = "select * " +
            "from events as e " +
            "WHERE e.id_category = ?1 ",
            nativeQuery = true)
    List<Event> getEventsByCategoriy(int id);

    @Query(value = "select * " +
            "from events as e " +
            "WHERE e.id = ?1 ",
            nativeQuery = true)
    Event getEventsById(int id);

    @Query(value = "select * " +
            "from events as e " +
            "WHERE e.id_initiator = ?1 " +
            "AND e.id=?2",
            nativeQuery = true)
    Event getUserEvet(int idUser, int idEvent);

    @Query(value = "select * " +
            "from events as e " +
            "WHERE  CAST(e.event_date AS timestamp) > ?3 " +
            "AND CAST(e.event_date AS timestamp)<?4 " +
            "AND e.id_category IN (?5) " +
            "AND e.id_initiator IN (?6)" +
            "LIMIT ?2 OFFSET ?1",
            nativeQuery = true)
    List<Event> getEventAdmin(int from, int size, LocalDateTime start, LocalDateTime end, int[] categories, int[] users, String[] state);

    @Query(value = "select * " +
            "from events as e " +
            "WHERE e.id IN(?1) ",
            nativeQuery = true)
    Set<Event> getEventForCompilation(Set<Integer> events);



}
