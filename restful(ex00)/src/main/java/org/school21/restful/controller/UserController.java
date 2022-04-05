package org.school21.restful.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.school21.restful.exception.DuplicateLoginException;
import org.school21.restful.exception.EntityNotFoundException;
import org.school21.restful.model.User;
import org.school21.restful.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Пользователи", description = "Управление информацией о пользователях")
public class UserController {
    private final UserService userService;

    @Operation(summary = "getAllUsers")
    @GetMapping(value = {"/users", "/users/page/{page-num}/size/{page-size}"})
    public ResponseEntity<List<User>> getAllUsers(@PathVariable(name = "page-num", required = false) Integer pageNum,
                                                  @PathVariable(name = "page-size", required = false) Integer pageSize) {
        Pageable pageable = pageNum == null || pageSize == null ? PageRequest.of(0, 10) : PageRequest.of(pageNum, pageSize);
        return ResponseEntity.ok().body(userService.getAllUsers(pageable));
    }

    @Operation(summary = "addNewUser")
    @PostMapping("/users")
    public ResponseEntity<Object> addNewUser(@RequestBody User user) {
        log.info("Получен запрос на добавление пользователя. User: {}", user.toString());
        try {
            return ResponseEntity.ok().body(userService.addNewUser(user).getId());
        } catch (DuplicateLoginException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "updateUser")
    @PutMapping("/users/{user-id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("user-id") Long userId) {
        User updatedUser = userService.updateUser(userId, user);
        return ResponseEntity.ok().body(updatedUser);
    }

    @Operation(summary = "deleteUser")
    @DeleteMapping("/users/{user-id}")
    public ResponseEntity<String> deleteUser(@PathVariable("user-id") Long userId) {
        try {
            userService.deleteUser(userId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("SUCCESS");
    }
}
