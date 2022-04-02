package org.school21.restful.service;

import org.school21.restful.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User addNewUser(User user);
    List<User> getAllUsers(Pageable pageable);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
