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
                return userRepository.save(user);
            } else {
                throw new ConflictException("");
            }
        } else {
            throw new RequestException("Неправильное тело запроса");
        }
    }

    @Override
    public List<User> getUser(int[] ids, int size, int from) {
        return userRepository.getUsers(ids, from, size);
    }

    ;

    @Override
    public void deletUser(int userId) {
        userRepository.deleteById(userId);
    }

    ;
}
