package in.ac.daiict.deep.util.status;

import in.ac.daiict.deep.constant.status.ResultStatusEnum;
import lombok.Getter;

public class ResultStatus {
    @Getter
    private static String statusName="result_status";
    private ResultStatusEnum statusValue;

    public ResultStatus(ResultStatusEnum statusValue) {
        this.statusValue = statusValue;
    }

    public String getStatusValue() {
        return statusValue.toString();
    }
}
