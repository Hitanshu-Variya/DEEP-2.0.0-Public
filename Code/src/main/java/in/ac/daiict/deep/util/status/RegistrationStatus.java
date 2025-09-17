package in.ac.daiict.deep.util.status;

import in.ac.daiict.deep.constant.status.RegistrationStatusEnum;
import lombok.Getter;

public class RegistrationStatus {
    @Getter
    private static String statusName="registration_status";
    private RegistrationStatusEnum statusValue;

    public RegistrationStatus(RegistrationStatusEnum statusValue) {
        this.statusValue = statusValue;
    }

    public String getStatusValue() {
        return statusValue.toString();
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = RegistrationStatusEnum.valueOf(statusValue);
    }

}
