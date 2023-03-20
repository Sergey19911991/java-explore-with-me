package ru.practicum.main.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select * " +
            "from users as u " +
            "WHERE u.id IN (?1) "+
            "ORDER BY u.id ASC "+
            "LIMIT ?3 OFFSET ?2",
            nativeQuery = true)
    List<User> getUsers(int[] ids,int from, int size);
}
