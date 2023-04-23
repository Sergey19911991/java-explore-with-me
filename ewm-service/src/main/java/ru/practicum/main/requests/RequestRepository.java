package ru.practicum.main.requests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    @Query(value = "select * " +
            "from requests as r " +
            "WHERE r.requester = ?1 " +
            "ORDER BY r.id ASC ",
            nativeQuery = true)
    List<Request> getRequestsUser(int id);

    @Query(value = "select * " +
            "from requests as r " +
            "WHERE r.requester = ?1 " +
            "AND r.id=?2 ",
            nativeQuery = true)
    Request getRequestsUser(int userId, int requestId);

    @Query(value = "select * " +
            "from requests as r " +
            "WHERE r.requester = ?1 " +
            "AND r.event=?2 ",
            nativeQuery = true)
    List<Request> getRequestsUserEvent(int userId, int eventId);


    @Query(value = "select * " +
            "from requests as r " +
            "WHERE r.id IN (?1) ",
            nativeQuery = true)
    List<Request> getRequests(List<Integer> requests);

    @Query(value = "select * " +
            "from requests as r " +
            "WHERE r.event = ?1 " +
            "ORDER BY r.id ASC ",
            nativeQuery = true)
    List<Request> getRequestsEvent(int id);

    @Query(value = "select * " +
            "from requests as r " +
            "WHERE r.event=?1 ",
            nativeQuery = true)
    List<Request> getRequestsUserEvent1(int eventId);


    @Query(value = "select * " +
            "from requests as r " +
            "WHERE r.event = ?1 " +
            "AND r.status='CONFIRMED' ",
            nativeQuery = true)
    List<Request> getRequestsEventConfirmed(int id);


    @Query(value = "select req.event.id, count(req) " +
            "from Request as req " +
            "WHERE req.event.id in (?1) " +
            "AND req.status='CONFIRMED' " +
            "Group by req.event.id")
    Map<Integer, Integer> getRequestsEventConfirmedMap(List<Integer> idList);


}
