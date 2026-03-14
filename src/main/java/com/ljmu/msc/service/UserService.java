package com.ljmu.msc.service;

import com.ljmu.msc.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(User user);

    Optional<User> getUser(Long id);

    List<User> getAllUsers();

    void deleteUser(Long id);
}
