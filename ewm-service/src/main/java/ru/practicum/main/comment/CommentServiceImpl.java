package ru.practicum.main.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.NewComment;
import ru.practicum.main.events.Event;
import ru.practicum.main.events.EventsRepository;
import ru.practicum.main.events.State;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.user.User;
import ru.practicum.main.user.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;

    private final EventsRepository eventsRepository;

    private final CommentRepository commentRepository;

    private final MappingComment mappingComment;

    @Override
    public NewComment creatComment(CommentDto commentDto, int idUser, int idEvent) {
        User user = userRepository.findById(idUser).orElseThrow(() -> new NotFoundException("Пользователь не найден!"));
        Event event = eventsRepository.findById(idEvent).orElseThrow(() -> new NotFoundException("Событие не найдено!"));
        if (event.getState() != State.PUBLISHED) {
            log.error("Нельзя добавить комментарий к неопубликованному событию");
            throw new ConflictException("Нельзя добавить комментарий к неопубликованному событию");
        }
        Comment comment = commentRepository.save(mappingComment.mappingCommentDto(commentDto, event, user));
        log.info("Создан комментарий");
        return mappingComment.mappingCommentNewComment(comment);
    }

    @Override
    public NewComment updateComment(CommentDto commentDto, int idUser, int idComment) {
        userRepository.findById(idUser).orElseThrow(() -> new NotFoundException("Пользователь не найден!"));
        Comment comment = commentRepository.findById(idComment).orElseThrow(() -> new NotFoundException("Комментарий не найден!"));
        if (comment.getUser().getId() != idUser) {
            log.error("Изменить комментарий может только пользователь, добавивший его");
            throw new ConflictException("Изменить комментарий может только пользователь, добавивший его");
        }
        comment = commentRepository.save(mappingComment.mappingUpdateComment(comment, commentDto));
        log.info("Изменен комментарий с id = {}", idComment);
        return mappingComment.mappingCommentNewComment(comment);
    }


    @Override
    public void deletComment(int idUser, int idComment) {
        userRepository.findById(idUser).orElseThrow(() -> new NotFoundException("Пользователь не найден!"));
        Comment comment = commentRepository.findById(idComment).orElseThrow(() -> new NotFoundException("Комментарий не найден!"));
        if (comment.getUser().getId() != idUser) {
            log.error("Удалить комментарий может только пользователь, добавивший его");
            throw new ConflictException("Удалить комментарий может только пользователь, добавивший его");
        }
        log.info("Удален комментарий с id = {}", idComment);
        commentRepository.deleteById(idComment);
    }

}
