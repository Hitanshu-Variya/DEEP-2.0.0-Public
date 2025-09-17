package in.ac.daiict.deep.service;

import in.ac.daiict.deep.dto.ResponseDto;

public interface OtpVerificationService {
    void generateOtpAndSendMail(String username, String email);
    ResponseDto verifyOtp(String username, String otp);
    void deleteExpiredOtp();
}
