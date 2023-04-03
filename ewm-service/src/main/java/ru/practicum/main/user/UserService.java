package ru.practicum.main.user;


import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto creatUser(NewUserRequest newUserRequest);

    List<UserDto> getUser(int[] ids, int size, int from);

    void deletUser(int userId);

}
