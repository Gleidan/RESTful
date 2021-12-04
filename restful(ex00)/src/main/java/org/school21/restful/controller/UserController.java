package org.school21.restful.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.school21.restful.exception.UserNotFoundException;
import org.school21.restful.model.User;
import org.school21.restful.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PostMapping("/users")
    public ResponseEntity<Long> addNewUser(@RequestBody User user) {
        log.info(user.toString());
        return ResponseEntity.ok().body(userService.addNewUser(user).getId());
    }

    @PutMapping("/users/{user-id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("user-id") Long userId) {
        User updatedUser = userService.updateUser(userId, user);
        return ResponseEntity.ok().body(updatedUser);
    }

    @DeleteMapping("/users/{user-id}")
    public ResponseEntity<String> deleteUser(@PathVariable("user-id") Long userId) {
        try {
            userService.deleteUser(userId);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("SUCCESS");
    }
}
