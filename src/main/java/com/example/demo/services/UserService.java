package com.example.demo.services;

import com.example.demo.model.User;

import java.util.Optional;

public interface UserService {
    void saveUser(User user);

    void deleteUser(Long userId);

    void blockUser(Long userId);

    void unblockUser(Long userId);

    Optional<User> findByTelegramId(Long telegramId);

}
