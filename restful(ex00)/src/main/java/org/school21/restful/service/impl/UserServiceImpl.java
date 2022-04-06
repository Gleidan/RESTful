package org.school21.restful.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.school21.restful.exception.DuplicateLoginException;
import org.school21.restful.exception.EntityNotFoundException;
import org.school21.restful.model.User;
import org.school21.restful.repository.UserRepository;
import org.school21.restful.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User addNewUser(User user) {
        User checkUser = userRepository.findByLogin(user.getLogin());
        if (checkUser != null) {
            throw new DuplicateLoginException(String.format("User with login %s already exists", checkUser.getLogin()));
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).getContent();
    }

    @Override
    public User updateUser(Long id, User user) {
        User userToUpdate = userRepository.getById(id);
        if (user.getFirstName() != null) {
            userToUpdate.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            userToUpdate.setLastName(user.getLastName());
        }
        if (user.getLogin() != null) {
            userToUpdate.setLogin(user.getLogin());
        }
        if (user.getPassword() != null) {
            userToUpdate.setPassword(user.getPassword());
        }
        if (user.getRole() != null) {
            userToUpdate.setRole(user.getRole());
        }
        return userRepository.save(userToUpdate);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found in database");
        }
        User user = userRepository.getById(id);
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User not found in the database");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
    }
}
