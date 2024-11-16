package com.infosys.Wheelwise.Controller;

import java.time.LocalDateTime;

import com.infosys.Wheelwise.Model.User;
import com.infosys.Wheelwise.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {

	@Autowired
	UserService userService;
	@RequestMapping("/signup")
    public String signup() {
        return "Sign Up page..";
    }
	 
//	 @PostMapping("/signup")
//	    public User addUser(@RequestBody User user) {
//		 if (userService.findByEmail(user.getEmail()).isPresent()) {
//	            return ResponseEntity.badRequest().body("Email already exists");
//	        }
//	        userService.createUser(user);
//	        return user;
//	    }
	 
	 @PostMapping("/signup")
	    public ResponseEntity<String> signup(@RequestBody User user) {
	        if (userService.findByEmail(user.getEmail()).isPresent()) {
	            return ResponseEntity.badRequest().body("Email already exists");
	        }

	        user.setCreatedAt(LocalDateTime.now());
	        user.setModifiedAt(LocalDateTime.now());
	        userService.registerUser(user);
	        return ResponseEntity.ok("User registered successfully");
	    }

}
