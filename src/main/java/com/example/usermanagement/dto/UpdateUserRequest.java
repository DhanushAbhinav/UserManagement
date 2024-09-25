package com.example.usermanagement.dto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UpdateUserRequest {
    private List<UUID> user_ids;
    private Map<String, Object> update_data;

    // Getters and Setters
    public List<UUID> getUser_ids() {
        return user_ids;
    }

    public void setUser_ids(List<UUID> user_ids) {
        this.user_ids = user_ids;
    }

    public Map<String, Object> getUpdate_data() {
        return update_data;
    }

    public void setUpdate_data(Map<String, Object> update_data) {
        this.update_data = update_data;
    }
}