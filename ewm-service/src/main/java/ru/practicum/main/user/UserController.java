package ru.practicum.main.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User creatUser(@RequestBody User user){
        log.info("контроллер");
        return userService.creatUser(user);}

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value="/{userId}")
    public void deletUser(@PathVariable int userId){
        userService.deletUser(userId);
    };

    @GetMapping
    public List<User> getUsers(@RequestParam(value = "ids") int[] ids, @RequestParam(value = "from",defaultValue ="0") int from, @RequestParam(value = "size", defaultValue = "10") int size){
          log.info("контроллер get");
          return userService.getUser(ids,size,from);
    }
}
