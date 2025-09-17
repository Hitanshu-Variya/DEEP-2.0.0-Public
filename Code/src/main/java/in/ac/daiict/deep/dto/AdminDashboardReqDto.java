package in.ac.daiict.deep.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminDashboardReqDto {
    private int semester;
    private long totalStudents;
    private long submittedPrefCnt;
    private boolean allocationStatus;
}
