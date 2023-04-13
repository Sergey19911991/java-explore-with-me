package ru.practicum.main.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Set;

public interface EventsRepository extends JpaRepository<Event, Integer>, QuerydslPredicateExecutor<Event> {
    @Query(value = "select * " +
            "from events as e " +
            "WHERE e.id_initiator = ?1 " +
            "ORDER BY e.id ASC " +
            "LIMIT ?3 OFFSET ?2",
            nativeQuery = true)
    List<Event> getEventsUser(int id, int from, int size);


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
            "WHERE e.id IN(?1) ",
            nativeQuery = true)
    Set<Event> getEventForCompilation(Set<Integer> events);


}
