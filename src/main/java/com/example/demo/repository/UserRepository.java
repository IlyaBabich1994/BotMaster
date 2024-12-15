package com.example.demo.repository;



import com.example.demo.model.User;

import java.util.ArrayList;
import java.util.List;

class UserRepository  {
    private List<User> users = new ArrayList<>();


    public User findByTelegramId(Long telegramId) {
        for (User user : users) {
            if (user.getTelegramId() == telegramId) {
                return user;
            }
        }
        return null;
    }
}

