package com.infosys.Wheelwise.repo;
import com.infosys.Wheelwise.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Long> {

    // Find OTP verification record by email
    Optional<OtpVerification> findByEmail(String email);

    // Find OTP verification record by email and OTP
    Optional<OtpVerification> findByEmailAndOtp(String email, String otp);

    // Optionally, delete OTP by email (useful for cleanup after verification)
    void deleteByEmail(String email);
}
