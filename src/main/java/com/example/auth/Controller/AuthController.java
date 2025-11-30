package com.example.auth.Controller;

import com.example.auth.Entity.User;
import com.example.auth.Repository.UserRepository;
import com.example.auth.Service.AuthService;
import com.example.auth.Service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final UserRepository userRepo;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return authService.login(user.getUsername(), user.getPassword());
    }

    @GetMapping("/hello")
    public String hello() {
        return "JWT Working!";
    }

    
    @GetMapping("/me")
    public ResponseEntity<?> getMe(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        Optional<User> user = userRepo.findByUsername(username);

        return user.<ResponseEntity<?>>map(u -> ResponseEntity.ok(u))
                .orElseGet(() -> ResponseEntity.status(404).body("User not found"));
    }
}
