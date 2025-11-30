
package com.example.auth.Service;

import com.example.auth.Entity.User;
import com.example.auth.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final JwtService jwtService;

    public String register(User user) {
        Optional<User> existingUser = userRepo.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return "Username already taken!";
        }
        userRepo.save(user);
        return "User Registered Successfully!";
    }

    public String login(String username, String password) {
        Optional<User> optionalUser = userRepo.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return "User not found!";
        }

        User user = optionalUser.get();
        if (user.getPassword().equals(password)) {
            return jwtService.generateToken(username);  
        } else {
            return "Invalid Credentials!";
        }
    }
}
