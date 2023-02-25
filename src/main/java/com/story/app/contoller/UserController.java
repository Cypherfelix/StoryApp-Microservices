package com.story.app.contoller;

import com.story.app.Exception.BadRequestException;
import com.story.app.Exception.ResourceNotFoundException;
import com.story.app.model.User;
import com.story.app.repository.UserRepository;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        System.out.println("Register");
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new BadRequestException("Username is already taken",
                HttpStatus.UNAUTHORIZED);
        }

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {

        Optional<User> u = userRepository.findByUsername(user.getUsername());
        if (u.isEmpty()) {
            throw new BadRequestException("Username does not exist", HttpStatus.UNAUTHORIZED);
        }
        if (!new BCryptPasswordEncoder().matches(user.getPassword(), u.get().getPassword())) {
            throw new BadRequestException("Incorrect Password", HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(u.get());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody User user) {
        User existingUser = userRepository.findByUsername(user.getUsername())
            .orElseThrow(() -> new ResourceNotFoundException(
                "User with username " + user.getUsername() + " is not available",
                HttpStatus.UNAUTHORIZED));

        existingUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        userRepository.save(existingUser);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Working");
    }


}