package com.example.usermanagement.service;

import com.example.usermanagement.dto.CreateUserRequest;
import com.example.usermanagement.dto.UpdateUserRequest;
import com.example.usermanagement.model.Manager;
import com.example.usermanagement.model.User;
import com.example.usermanagement.repository.ManagerRepository;
import com.example.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ManagerRepository managerRepository;


    public ResponseEntity<String> createUser(CreateUserRequest request) {
        // Validate and create user
        User user = new User();
        user.setFull_name(request.getFull_name());
        user.setMob_num(request.getMob_num()); // Adjust country code
        user.setPan_num(request.getPan_num().toUpperCase());

        if (request.getManager_id() != null) {
            Optional<Manager> manager = managerRepository.findById(request.getManager_id());
            if (manager.isPresent()) {
                user.setManager_id(request.getManager_id());
            } else {
                return ResponseEntity.badRequest().body("Invalid manager_id");
            }
        }

        userRepository.save(user);
        return ResponseEntity.ok("User created successfully");
    }

    public List<User> getUsers(String mob_num, String user_id, String manager_id) {
        // Fetch users based on parameters
        if (mob_num != null) {
            return userRepository.findByMobNum(mob_num);
        } else if (user_id != null) {
            return List.of(userRepository.findById(UUID.fromString(user_id)).orElse(null));
        } else if (manager_id != null) {
            return userRepository.findByManagerId(UUID.fromString(manager_id));
        } else {
            return userRepository.findAll();
        }
    }

    public ResponseEntity<String> deleteUser(String user_id) {
        Optional<User> user = userRepository.findById(UUID.fromString(user_id));
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    public ResponseEntity<String> updateUser(UpdateUserRequest request) {
        List<UUID> userIds = request.getUser_ids();
        Map<String, Object> updateData = request.getUpdate_data();

        for (UUID userId : userIds) {
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                // Update data individually or based on bulk manager_id update
                if (updateData.containsKey("manager_id") && updateData.size() == 1) {
                    UUID newManagerId = UUID.fromString(updateData.get("manager_id").toString());

                    Optional<Manager> manager = managerRepository.findById(newManagerId);
                    if (manager.isPresent()) {
                        user.setManager_id(newManagerId);
                        user.setUpdated_at(LocalDateTime.now());
                    } else {
                        return ResponseEntity.badRequest().body("Invalid manager_id");
                    }
                } else {
                    // Individual updates
                    if (updateData.containsKey("full_name")) {
                        user.setFull_name(updateData.get("full_name").toString());
                    }
                    if (updateData.containsKey("mob_num")) {
                        user.setMob_num(updateData.get("mob_num").toString());
                    }
                    if (updateData.containsKey("pan_num")) {
                        user.setPan_num(updateData.get("pan_num").toString().toUpperCase());
                    }
                    if (updateData.containsKey("manager_id")) {
                        return ResponseEntity.badRequest().body("manager_id cannot be updated in bulk");
                    }
                    user.setUpdated_at(LocalDateTime.now());
                }

                userRepository.save(user);
            } else {
                return ResponseEntity.badRequest().body("User with ID " + userId + " not found");
            }
        }

        return ResponseEntity.ok("User(s) updated successfully");
    }
}