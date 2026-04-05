package com.example.demo.dto;

import com.example.demo.model.Role;

public class UserResponseDto {
    private Long id;
    private String username;
    private Role role;
    private boolean active;

    public UserResponseDto(Long id, String username, Role role, boolean active) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.active = active;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public Role getRole() { return role; }
    public boolean isActive() { return active; }
}
