package ru.practicum.main.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public User creatUser(User user){
        log.info("сервисе");
        log.info("Создан пользователь");
        return userRepository.save(user);
    }

    @Override
    public List<User> getUser(int[] ids,int size,int from){
        log.info("get");
         return userRepository.getUsers(ids,from,size);
    };

    @Override
    public void deletUser(int userId){
        userRepository.deleteById(userId);
    };
}
