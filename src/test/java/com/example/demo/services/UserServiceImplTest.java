package com.example.demo.services;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static com.example.demo.util.Creater.createUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserService userService = new UserServiceImpl(userRepository);

    @Test
    public void testCreateUser() {
        User user = createUser();
        userService.saveUser(user);
        verify(userRepository, only()).save(user);
    }

    @Test
    public void testDeleteUser() {
        User user = createUser();
        userService.deleteUser(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());
    }
    @Test
    public void testDeleteUserNotFound(){
        Long userId = 2L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        userService.deleteUser(userId);
        verify(userRepository,never()).delete(any(User.class));
    }
    @Test
    public void testBlockUser(){
        User user = createUser();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.blockUser(user.getId());
        assertTrue(user.isStatus());
        verify(userRepository,times(1)).save(user);
    }

    @Test
    public void testUnblockUser(){
        User user = createUser();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.unblockUser(user.getId());
        assertFalse(user.isStatus());
        verify(userRepository,times(1)).save(user);
    }

    @Test
    public void testBlockUserNotFound(){
        User user = createUser();
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        userService.blockUser(user.getId());
        verify(userRepository,never()).save(user);
    }
    @Test
    public void testUnblockUserNotFound(){
        User user = createUser();
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        userService.unblockUser(user.getId());
        verify(userRepository,never()).save(user);
    }
    @Test
    public void testFindByTelegramId(){
        Long telegramId = 1L;
        User expectedUser = createUser();
        when(userRepository.findByTelegramId(telegramId)).thenReturn(Optional.of(expectedUser));
        User result = userService.findByTelegramId(telegramId);
        verify(userRepository,only()).findByTelegramId(telegramId);
        assertEquals(expectedUser,result);
    }
    @Test
    public void testFindByTelegramIdShouldThrowException(){
        Long telegramId = 1L;
        when(userRepository.findByTelegramId(telegramId)).thenThrow(new RuntimeException("User not found"));
        assertThrows(RuntimeException.class,()->userService.findByTelegramId(telegramId));
        verify(userRepository,only()).findByTelegramId(any());
    }
}