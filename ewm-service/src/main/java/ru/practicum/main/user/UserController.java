package ru.practicum.main.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;

import javax.validation.Valid;
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
    public UserDto creatUser(@RequestBody @Valid NewUserRequest user) {
        log.info("Создан пользователь");
        return userService.creatUser(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{userId}")
    public void deletUser(@PathVariable int userId) {
        log.info("Удален пользователь с id = {}",userId);
        userService.deletUser(userId);
    }

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(value = "ids", required = false) int[] ids, @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero int from, @RequestParam(value = "size", defaultValue = "10") @Positive int size) {
        log.info("Информация о пользователях");
        return userService.getUser(ids, size, from);
    }

}
