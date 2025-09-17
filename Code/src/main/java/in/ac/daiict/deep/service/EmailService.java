package in.ac.daiict.deep.service;

public interface EmailService {
    void sendOtp(String username, String to, String otp);
}
