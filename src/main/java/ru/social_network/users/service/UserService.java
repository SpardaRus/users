package ru.social_network.users.service;

import org.springframework.stereotype.Service;
import ru.social_network.users.entity.User;
import ru.social_network.users.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
