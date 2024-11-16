package com.infosys.Wheelwise.Services;

import java.time.LocalDateTime;
import java.util.Optional;

import com.infosys.Wheelwise.OtpVerification;
import com.infosys.Wheelwise.repo.OtpVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OtpService {

    @Autowired
    private OtpVerificationRepository otpVerificationRepository;

    public void saveOtp(String email, String otp, LocalDateTime expirationTime) {
        OtpVerification otpVerification = new OtpVerification();
        otpVerification.setEmail(email);
        otpVerification.setOtp(otp);
        otpVerification.setExpirationTime(expirationTime);
        otpVerificationRepository.save(otpVerification);
    }

    public Optional<OtpVerification> verifyOtp(String email, String otp) {
        return otpVerificationRepository.findByEmailAndOtp(email, otp);
    }

    public void deleteOtp(String email) {
        otpVerificationRepository.deleteByEmail(email);
    }
}
