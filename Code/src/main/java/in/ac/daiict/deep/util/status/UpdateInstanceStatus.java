package in.ac.daiict.deep.util.status;

import in.ac.daiict.deep.constant.status.UpdateInstanceStatusEnum;
import lombok.Getter;


public class UpdateInstanceStatus {
    @Getter
    private static String statusName="update_instance_status";
    private UpdateInstanceStatusEnum statusValue;

    public UpdateInstanceStatus(UpdateInstanceStatusEnum statusValue) {
        this.statusValue = statusValue;
    }

    public String getStatusValue() {
        return statusValue.toString();
    }
}

