package com.infosys.Wheelwise.Controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.infosys.Wheelwise.Model.User;
import com.infosys.Wheelwise.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class SignUpController {

	@Autowired

	UserService userService;

	 
//	 @PostMapping("/signup")
//	    public User addUser(@RequestBody User user) {
//		 if (userService.findByEmail(user.getEmail()).isPresent()) {
//	            return ResponseEntity.badRequest().body("Email already exists");
//	        }
//	        userService.createUser(user);
//	        return user;
//	    }
	 @ResponseBody
	 @PostMapping("/register")
	 public ResponseEntity<Map<String, String>> signup(@RequestBody User user) {
		 Map<String, String> response = new HashMap<>();

		 if (userService.findByEmail(user.getEmail()).isPresent()) {
			 response.put("message", "Email already exists");
			 return ResponseEntity.badRequest().body(response);
		 }

		 user.setCreatedAt(LocalDateTime.now());
		 user.setModifiedAt(LocalDateTime.now());
		 userService.registerUser(user);

		 response.put("message", "User registered successfully");
		 return ResponseEntity.ok(response);
	 }

}
