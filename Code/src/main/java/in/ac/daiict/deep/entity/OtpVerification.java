package in.ac.daiict.deep.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "otp_verification")
public class OtpVerification {
    @Id
    @Column(length = 100)
    private String username;
    @Column(length = 6)
    private String otp;
    @Column(name = "expiry_time")
    private LocalDateTime expiryTime;
}
