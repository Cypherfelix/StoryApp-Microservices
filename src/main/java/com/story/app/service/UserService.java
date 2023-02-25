package com.story.app.service;

import com.story.app.model.User;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public ResponseEntity<?> login(User user) {

        return new ResponseEntity<>("", HttpStatusCode.valueOf(200));
    }
}
