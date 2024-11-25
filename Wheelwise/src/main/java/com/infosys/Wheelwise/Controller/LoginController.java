package com.infosys.Wheelwise.Controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
	public ResponseEntity<Map<String, String>> signin(@RequestBody User user) {
		Map<String, String> response = new HashMap<>();
		Optional<User> optionalUser = userService.findByEmail(user.getEmail());
		if (optionalUser.isEmpty() || !optionalUser.get().getPassword().equals(user.getPassword())) {
			response.put("message", "Invalid email or password");
			return ResponseEntity.badRequest().body(response);
		}
		User foundUser = optionalUser.get();

		foundUser.setLoggedIn(true);  // Set the 'isLoggedIn' field to true
		userService.registerUser(foundUser);

		response.put("message", "Login successful");
		return ResponseEntity.ok(response);

	}


	
}
