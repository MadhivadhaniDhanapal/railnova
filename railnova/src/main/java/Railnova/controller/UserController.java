package Railnova.controller;

import Railnova.entity.User;
import Railnova.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody User user) {

        Optional<User> loggedUser =
                userService.loginUser(user.getEmail(), user.getPassword());

        if (loggedUser.isPresent()) {
            return loggedUser.get();
        }

        return null;
    }
}