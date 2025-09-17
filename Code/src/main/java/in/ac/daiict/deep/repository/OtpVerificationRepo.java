package in.ac.daiict.deep.repository;

import in.ac.daiict.deep.entity.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface OtpVerificationRepo extends JpaRepository<OtpVerification,String> {
    boolean existsByOtp(String otp);
    void deleteByExpiryTimeBefore(LocalDateTime time);
}
