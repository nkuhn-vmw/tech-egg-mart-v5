package com.techegg.service;

import com.techegg.dto.UserRequest;
import com.techegg.dto.UserResponse;
import com.techegg.entity.User;
import com.techegg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse register(UserRequest request) {
        // check if username or email already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<String> roles = request.getRoles();
        if (roles != null && !roles.isEmpty()) {
            user.setRoles(roles);
        } else {
            user.setRoles(Set.of("ROLE_USER"));
        }
        user.setAccountStatus(User.AccountStatus.ACTIVE);
        User saved = userRepository.save(user);
        return UserResponse.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public UserResponse login(String username, String rawPassword) {
        Optional<User> opt = userRepository.findByUsername(username);
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        User user = opt.get();
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        return UserResponse.fromEntity(user);
    }

    @Transactional
    public UserResponse assignRoles(Long userId, Set<String> roles) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setRoles(roles);
        User saved = userRepository.save(user);
        return UserResponse.fromEntity(saved);
    }
}
