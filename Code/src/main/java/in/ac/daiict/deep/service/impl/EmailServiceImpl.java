package in.ac.daiict.deep.service.impl;

import in.ac.daiict.deep.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private JavaMailSender javaMailSender;

    public void sendOtp(String username, String to, String otp) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            String htmlContent = generateEmailTemplate(username, otp);
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject("DEEP: Password Reset Verification Code");
            helper.setText(htmlContent, true);
            javaMailSender.send(message);
        } catch (Exception me) {
            log.error("Failed to send mail to user: {} with error: {}", username, me.getMessage(), me);
        }
    }

    private String generateEmailTemplate(String username, String otp) {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8" />
                    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                    <title>Verify your Email - DEEP</title>
                    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet" />
                    <style>
                        body {
                          margin: 0;
                          padding: 0;
                          background: #E3F2FD;
                          font-family: 'Inter', sans-serif;
                        }
                        .wrapper {
                          background: linear-gradient(135deg, #E3F2FD 0%%, #BBDEFB 100%%);
                          padding: 20px;
                        }
                        .email-container {
                          max-width: 600px;
                          margin: 0 auto;
                          background: linear-gradient(135deg, #FFFFFF 0%%, #F8FAFE 100%%);
                          border-radius: 16px;
                          box-shadow: 0 8px 32px rgba(25, 118, 210, 0.12);
                          border: 1px solid rgba(25, 118, 210, 0.08);
                          overflow: hidden;
                        }
                        .top-bar {
                          height: 4px;
                          background: linear-gradient(90deg, #1565C0, #1976D2, #2196F3);
                        }
                        .email-header {
                          padding: 30px 40px 20px;
                        }
                        .email-logo {
                          display: flex;
                          align-items: center;
                          gap: 10px;
                        }
                        .email-logo img {
                          width: 40px;
                          height: 40px;
                        }
                        .email-logo-text {
                          font-weight: 700;
                          font-size: 28px;
                          color: #0D47A1;
                        }
                        .email-logo-sub {
                          font-weight: 500;
                          font-size: 11px;
                          color: #1565C0;
                          opacity: 0.8;
                          line-height: 1.3;
                        }
                        .gradient-divider {
                          height: 2px;
                          background: linear-gradient(90deg, transparent, #1976D2, #2196F3, #1976D2, transparent);
                          margin: 12px 0;
                          border-radius: 1px;
                        }
                        .email-content {
                          padding: 0 40px 20px;
                          color: #0D47A1;
                        }
                        .email-content h1 {
                          color: #1565C0;
                          font-size: 24px;
                          font-weight: 600;
                          margin: 0 0 10px;
                        }
                        .email-content p {
                          font-size: 15px;
                          line-height: 1.6;
                          margin: 0 0 30px;
                          color: #1565C0;
                        }
                        .otp-box {
                          display: inline-block;
                          background: linear-gradient(135deg, #1565C0 0%%, #1976D2 100%%);
                          border-radius: 12px;
                          padding: 20px 30px;
                          box-shadow: 0 4px 16px rgba(25, 118, 210, 0.25);
                          border: 1px solid rgba(25, 118, 210, 0.2);
                          font-size: 42px;
                          font-weight: 600;
                          color: white;
                          letter-spacing: 6px;
                          text-shadow: 0 2px 4px rgba(0, 0, 0, 0.15);
                          font-family: 'SF Mono', 'Monaco', 'Consolas', monospace;
                          margin: 0 auto;
                        }
                        .centered {
                          text-align: center;
                        }
                        .note, .expiry {
                          background: rgba(25, 118, 210, 0.04);
                          border: 1px solid rgba(25, 118, 210, 0.15);
                          border-radius: 8px;
                          padding: 12px;
                          font-size: 14px;
                          color: #1565C0;
                          font-weight: 500;
                          text-align: center;
                          margin-top: 20px;
                        }
                        .security {
                          background: rgba(13, 71, 161, 0.03);
                          border: 1px solid rgba(13, 71, 161, 0.1);
                          border-radius: 8px;
                          padding: 5px;
                          margin-top: 10px;
                          font-size: 14px;
                          color: #1565C0;
                          text-align: center;
                        }
                        .footer {
                          background: rgba(25, 118, 210, 0.02);
                          padding: 16px 40px;
                          font-size: 12px;
                          color: #1565C0;
                          opacity: 0.7;
                          border-top: 1px solid rgba(25, 118, 210, 0.06);
                          text-align: center;
                        }
                        @media (max-width: 600px) {
                          .email-header, .email-content, .footer {
                            padding: 20px;
                          }
                          .email-logo img {
                            width: 28px;
                            height: 28px;
                          }
                          .email-logo-text {
                            font-size: 20px;
                          }
                          .email-logo-sub {
                            font-size: 10px;
                          }
                          .email-content h1 {
                            color: #1565C0;
                            font-size: 18px;
                          }
                          .email-content p {
                            font-size: 13px;
                          }
                          .otp-box {
                            font-size: 28px;
                            padding: 14px 22px;
                            letter-spacing: 4px;
                          }
                          .note, .security {
                            font-size: 12px;
                            padding: 10px;
                          }
                          .footer {
                            font-size: 10px;
                          }
                        }
                    </style>
                </head>
                <body>
                <div class="wrapper">
                    <div class="email-container">
                        <div class="top-bar"></div>
                        <div class="email-header">
                                <div>
                                    <div class="email-logo-text">DEEP</div>
                                    <div class="email-logo-sub">DAU Electives Enrollment Portal</div>
                                </div>
                            <div class="gradient-divider"></div>
                        </div>
                        <div class="email-content">
                            <h1>Hello %s,</h1>
                            <p>
                                We received a request to reset your password for your DEEP account. To proceed with the password reset, please use the verification code provided below:
                            </p>
                            <div class="centered" style="margin: 35px 0;">
                                <div class="otp-box">%s</div>
                            </div>
                            <div class="centered" style="margin: 25px 0;">
                                <div style="font-size: 16px; color: #1565C0; margin-bottom: 10px; line-height: 1.5;">
                                    Enter this verification code on the OTP Verification page to reset your password.
                                </div>
                            </div>
                            <div class="note">
                                Important: This verification code will expire in 10 minutes for security purposes.
                            </div>
                            <div class="security">
                                <p>If you did not request a password reset, please ignore this email and your password will remain unchanged.</p>
                                <p style="margin: 0px">For security concerns, do not share this OTP with anyone.</p>
                            </div>
                        </div>
                        <div class="footer">
                            This is an automated message. Please do not reply to this email.
                            <span style="display: none; font-size: 1px; color: #fff;">
                              _DEEP_UNIQUE_MARKER_RANDOM_%d_
                            </span>
                        </div>
                    </div>
                </div>
                </body>
                </html>
                """.formatted(username, otp, new Random().nextInt());
    }
}