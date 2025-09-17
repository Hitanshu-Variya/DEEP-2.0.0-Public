package in.ac.daiict.deep.util.status;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

public class RegistrationCloseDate {
    @Getter
    private static String statusName="registration_close_date";
    @Getter
    private LocalDate closeDate;

    public RegistrationCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }
    public RegistrationCloseDate(String closeDate) {
        this.closeDate = LocalDate.parse(closeDate);
    }

    public String getStringCloseDate() {
        return closeDate.toString();
    }
}
