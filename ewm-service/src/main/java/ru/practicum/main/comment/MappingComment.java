package ru.practicum.main.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.NewComment;
import ru.practicum.main.events.Event;
import ru.practicum.main.user.MappingUser;
import ru.practicum.main.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class MappingComment {
    private final MappingUser mappingUser;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Comment mappingCommentDto(CommentDto commentDto, Event event, User user) {
        Comment comment = new Comment();
        comment.setCreated(LocalDateTime.now());
        comment.setText(commentDto.getText());
        comment.setUser(user);
        comment.setEvent(event);
        return comment;
    }

    public NewComment mappingCommentNewComment(Comment comment) {
        NewComment newComment = new NewComment();
        newComment.setId(comment.getId());
        newComment.setCreated(comment.getCreated().format(formatter));
        newComment.setText(comment.getText());
        newComment.setUser(mappingUser.userDtoUser(comment.getUser()));
        newComment.setEventId(comment.getEvent().getId());
        return newComment;
    }

    public Comment mappingUpdateComment(Comment comment, CommentDto commentDto) {
        comment.setText(commentDto.getText());
        return comment;
    }
}
