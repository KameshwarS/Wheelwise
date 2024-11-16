package com.infosys.Wheelwise.Controller;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import com.infosys.Wheelwise.Model.User;
import com.infosys.Wheelwise.OtpVerification;
import com.infosys.Wheelwise.Services.EmailSenderService;
import com.infosys.Wheelwise.Services.UserService;
import com.infosys.Wheelwise.repo.OtpVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ForgotPasswordController {

	
	@Autowired
	private EmailSenderService emailSenderService;
	
	@Autowired
	UserService userService;
	@Autowired
	OtpVerificationRepository otpVerificationRepository;

	@RequestMapping("/forgot-password")
	public String renderForgotPasswordPage() {
	    return "forgot-password"; // Return view name, e.g., "forgot-password.jsp"
	}
	
	@PostMapping("/send-otp")
	public ResponseEntity<String> sendOtp(@RequestParam String email) {
	    User foundUser = userService.findByEmail(email).orElse(null);

	    if (foundUser == null) {
	        return ResponseEntity.badRequest().body("Email not found");
	    }

	    String otp = generateOtp(); // Implement OTP generation logic
	    OtpVerification otpVerification = new OtpVerification();
	    otpVerification.setEmail(email);
	    otpVerification.setOtp(otp);
	    otpVerification.setExpirationTime(LocalDateTime.now().plusMinutes(5));
	    otpVerificationRepository.save(otpVerification);


	    emailSenderService.sendSimpleEmail(email, "Your OTP", "Your OTP is: " + otp);
	    return ResponseEntity.ok("OTP sent successfully");
	}

	private String generateOtp() {
	    return String.valueOf(new Random().nextInt(900000) + 100000); // Generates a 6-digit OTP
	}
	
	@PostMapping("/set-password")
	public ResponseEntity<String> setNewPassword(@RequestParam String email,
	                                             @RequestParam String otp,
	                                             @RequestParam String newPassword,
	                                             @RequestParam String confirmPassword) {
	    // Check OTP and expiration time in OtpVerification
	    Optional<OtpVerification> otpVerification = otpVerificationRepository.findByEmail(email);

	    if (otpVerification.isEmpty() || !otpVerification.get().getOtp().equals(otp) ||
	        LocalDateTime.now().isAfter(otpVerification.get().getExpirationTime())) {
	        return ResponseEntity.badRequest().body("Invalid or expired OTP");
	    }

	    // Validate that newPassword and confirmPassword match
	    if (!newPassword.equals(confirmPassword)) {
	        return ResponseEntity.badRequest().body("Passwords do not match");
	    }

	    // Securely update the password using the UserService
	    boolean passwordUpdated = userService.updateUserPassword(email, newPassword);  // Ensure hashing in the updateUserPassword method

	    if (!passwordUpdated) {
	        return ResponseEntity.status(500).body("Error updating password");
	    }

	    // Clear OTP record after successful password reset
	    otpVerificationRepository.delete(otpVerification.get());

	    return ResponseEntity.ok("Password reset successfully");
	}

}
