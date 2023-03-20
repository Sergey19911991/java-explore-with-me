package ru.practicum.main.user;

import org.springframework.stereotype.Service;

import java.util.List;
public interface UserService {
      User creatUser(User user);

      List<User> getUser(int[] ids,int size,int from);

      void deletUser(int userId);

}
