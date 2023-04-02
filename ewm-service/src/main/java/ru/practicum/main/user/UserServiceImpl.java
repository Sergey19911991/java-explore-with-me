package ru.practicum.main.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.RequestException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User creatUser(User user) {
        if (user.getName() != null) {
            List<User> users = userRepository.getName(user.getName());
            if (users.size() == 0) {
                log.info("Создан пользователь {}",user);
                return userRepository.save(user);
            } else {
                log.error("Добавление пользователя с занятым именем!");
                throw new ConflictException("Добавление пользователя с занятым именем!");
            }
        } else {
            log.error("Неправильное тело запроса!");
            throw new RequestException("Неправильное тело запроса!");
        }
    }

    @Override
    public List<User> getUser(int[] ids, int size, int from) {
        if (ids != null && ids.length != 0) {
            log.info("Информация о {} пользователях с id {}, начиная с пользователя {} в списке, отсортированном по возрастанию id.",size,ids,from);
            return userRepository.getUsers(ids, from, size);
        } else {
            log.info("Информация о {} пользователях, начиная с пользователя {} в списке, отсортированном по возрастанию id.",size,from);
            return userRepository.getUsersIdsNull(from, size);
        }
    }

    @Override
    public void deletUser(int userId) {
        log.info("Удален пользователь с id {}",userId);
        userRepository.deleteById(userId);
    }

}
