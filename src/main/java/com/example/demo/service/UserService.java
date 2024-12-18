package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {
    void saveUser(User user);
    void deleteUser(Long userId);

    void blockUser(Long userId);

    void unblockUser(Long userId);
    User findByTelegramId(Long telegramId);

}
