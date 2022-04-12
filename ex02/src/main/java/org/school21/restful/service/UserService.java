package org.school21.restful.service;

import org.school21.restful.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User findByLoginAndPassword(String login, String pass);
}
