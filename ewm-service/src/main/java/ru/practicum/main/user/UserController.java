package ru.practicum.main.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@Slf4j
@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User creatUser(@RequestBody User user) {
        return userService.creatUser(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{userId}")
    public void deletUser(@PathVariable int userId) {
        userService.deletUser(userId);
    }

    @GetMapping
    public List<User> getUsers(@RequestParam(value = "ids", required = false) int[] ids, @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero int from, @RequestParam(value = "size", defaultValue = "10") @Positive int size) {
        return userService.getUser(ids, size, from);
    }

}
