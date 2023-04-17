package ru.practicum.main.comment;

import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.NewComment;

public interface CommentService {
    NewComment creatComment(CommentDto commentDto, int idUser, int idEvent);

    NewComment updateComment(CommentDto commentDto, int idUser, int idComment);

    void deletComment(int idUser, int idComment);
}
