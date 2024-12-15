package com.example.demo.Services;

import com.example.demo.model.User;

public interface UserService {
    String blockUser (User user); // отдаём стрингу + состояние
    String unblockUser(User user); // стринга + состояние
}
