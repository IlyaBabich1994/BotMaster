package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImplUserService implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public ImplUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void blockUser(Long userId) {
        userRepository.blockUser(userId);
    }

    @Override
    public void unblockUser(Long userId) {
        userRepository.unblockUser(userId);
    }

    @Override
    public User findByTelegramId(Long telegramId) {
        return userRepository.findByTelegramId(telegramId).orElseThrow(
                () -> new RuntimeException("User not found")
        );
    }
}
