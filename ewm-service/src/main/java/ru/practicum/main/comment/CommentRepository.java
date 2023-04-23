package ru.practicum.main.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query(value = "select * " +
            "from comment as c " +
            "WHERE c.id_event = ?1 ",
            nativeQuery = true)
    List<Comment> getCommentByIdEvent(int idEvent);

}
