package com.example.usermanagement.controller;

import com.example.usermanagement.dto.CreateUserRequest;
import com.example.usermanagement.dto.UpdateUserRequest;
import com.example.usermanagement.model.User;
import com.example.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create_user")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @PostMapping("/get_users")
    public ResponseEntity<List<User>> getUsers(@RequestParam(required = false) String mob_num,
            @RequestParam(required = false) String user_id, @RequestParam(required = false) String manager_id) {
        return ResponseEntity.ok(userService.getUsers(mob_num, user_id, manager_id));
    }

    @PostMapping("/delete_user")
    public ResponseEntity<String> deleteUser(@RequestBody String user_id) {
        return userService.deleteUser(user_id);
    }

    @PostMapping("/update_user")
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserRequest request) {
        return userService.updateUser(request);
    }
}