package ru.practicum.main.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final MappingUser mappingUser;

    @Override
    public UserDto creatUser(NewUserRequest newUserRequest) {
        List<User> users = userRepository.getName(newUserRequest.getName());
        if (users.size() == 0) {
            log.info("Создан пользователь {}", newUserRequest);
            User user = mappingUser.userNewUserRequest(newUserRequest);
            userRepository.save(user);
            return mappingUser.userDtoUser(user);
        } else {
            log.error("Добавление пользователя с занятым именем!");
            throw new ConflictException("Добавление пользователя с занятым именем!");
        }
    }

    @Override
    public List<UserDto> getUser(int[] ids, int size, int from) {
        List<User> users;
        if (ids != null && ids.length != 0) {
            log.info("Информация о {} пользователях с id {}, начиная с пользователя {} в списке, отсортированном по возрастанию id.", size, ids, from);
            users = userRepository.getUsers(ids, from, size);
        } else {
            log.info("Информация о {} пользователях, начиная с пользователя {} в списке, отсортированном по возрастанию id.", size, from);
            users = userRepository.getUsersIdsNull(from, size);
        }
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(mappingUser.userDtoUser(user));
        }
        return userDtos;
    }

    @Override
    public void deletUser(int userId) {
        log.info("Удален пользователь с id {}", userId);
        userRepository.deleteById(userId);
    }

}
