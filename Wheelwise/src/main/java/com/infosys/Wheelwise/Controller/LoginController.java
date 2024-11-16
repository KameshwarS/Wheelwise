package com.infosys.Wheelwise.Controller;

import java.util.Optional;

import com.infosys.Wheelwise.Model.User;
import com.infosys.Wheelwise.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController

public class LoginController {
	
	@Autowired
	UserService userService;
	@RequestMapping("/signin")
    public String login() {
        return "Login page..";
    }
	@PostMapping("/signin")
	public ResponseEntity<String> signin(@RequestBody User user) {
	    // Retrieve user by email
	    Optional<User> optionalUser = userService.findByEmail(user.getEmail());

	    // Check if user exists and password is correct
	    if (optionalUser.isEmpty() || !optionalUser.get().getPassword().equals(user.getPassword())) {
	        return ResponseEntity.badRequest().body("Invalid email or password");
	    }

	    // Get the user object from Optional
	    User foundUser = optionalUser.get();

	    // Set login status and save changes
	    foundUser.setLoggedIn(true);  // Set the 'isLoggedIn' field to true
	    userService.registerUser(foundUser);  // Persist the login status

	    // Return the full user details upon successful sign-in
	    return ResponseEntity.ok("Login successful");
	}


	
}
