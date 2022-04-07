package org.school21.restful.controller;

import lombok.AllArgsConstructor;
import org.school21.restful.jwt.JwtProvider;
import org.school21.restful.model.AuthRequest;
import org.school21.restful.model.AuthResponse;
import org.school21.restful.model.User;
import org.school21.restful.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(user.getLogin());
        return new AuthResponse(token);
    }
}
