package com.infosys.Wheelwise.Services;

import java.util.List;
import java.util.Optional;

import com.infosys.Wheelwise.Model.User;
import com.infosys.Wheelwise.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

		@Autowired
	    private UserRepository userRepository;
	   
	    // Method to create a new user
	   
	    public User registerUser(User user) {
	        return userRepository.save(user);
	    }

	    // Method to retrieve all users
	    public List<User> getAllUsers() {
	        return userRepository.findAll();
	    }

	    // Method to retrieve a user by ID
	    public User getUserById(Integer userId) {
	        return userRepository.findById(userId).orElse(new User());
	    }
	    public Optional<User> findByEmail(String email) {
	        return userRepository.findByEmail(email);
	    }
	    public boolean updateUserPassword(String email, String newPassword) {
	        Optional<User> foundUser = userRepository.findByEmail(email);
	        if (foundUser.isPresent()) {
	            User user = foundUser.get();
	            user.setPassword(newPassword);  // Save the password directly without encoding
	            userRepository.save(user);
	            return true;
	        }
	        return false;  // Return false if user is not found
	    }
}
