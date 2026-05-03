package com.techegg.dto;

import com.techegg.entity.User;
import java.util.Set;

public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Set<String> roles;
    private User.AccountStatus accountStatus;

    public UserResponse() {}

    public UserResponse(Long id, String username, String email, Set<String> roles, User.AccountStatus accountStatus) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.accountStatus = accountStatus;
    }

    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles(),
                user.getAccountStatus()
        );
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
    public User.AccountStatus getAccountStatus() { return accountStatus; }
    public void setAccountStatus(User.AccountStatus accountStatus) { this.accountStatus = accountStatus; }
}
