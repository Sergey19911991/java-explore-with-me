package ru.practicum.main.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.NewComment;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/users/{idUser}/events/{idEvent}")
    public NewComment creatComment(@RequestBody @Valid CommentDto commentDto, @PathVariable(value = "idUser") int idUser, @PathVariable(value = "idEvent") int idEvent) {
        log.info("Создан комментарий");
        return commentService.creatComment(commentDto, idUser, idEvent);
    }

    @PatchMapping(value = "/{idComment}/users/{idUser}")
    public NewComment updateComment(@RequestBody @Valid CommentDto commentDto, @PathVariable(value = "idUser") int idUser, @PathVariable(value = "idComment") int idComment) {
        log.info("Изменен комментарий с id = {}", idComment);
        return commentService.updateComment(commentDto, idUser, idComment);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{idComment}/users/{idUser}")
    public void deletComment(@PathVariable(value = "idUser") int idUser, @PathVariable(value = "idComment") int idComment) {
        commentService.deletComment(idUser, idComment);
    }
}
