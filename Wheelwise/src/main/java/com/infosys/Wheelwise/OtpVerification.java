package com.infosys.Wheelwise;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class OtpVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public OtpVerification() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OtpVerification(Long id, String email, String otp, LocalDateTime expirationTime) {
		super();
		this.id = id;
		this.email = email;
		this.otp = otp;
		this.expirationTime = expirationTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public LocalDateTime getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(LocalDateTime expirationTime) {
		this.expirationTime = expirationTime;
	}
	private String email;
    private String otp;
    private LocalDateTime expirationTime;

    // Getters and setters
}

