package in.ac.daiict.deep.service;

import in.ac.daiict.deep.dto.AllocationStatusDto;
import in.ac.daiict.deep.entity.AllocationStatus;

import java.util.List;

public interface AllocationStatusService {
    void insertAllocationStatus(AllocationStatus allocationStatus);
    List<AllocationStatusDto> fetchAll();
    boolean checkIfExists(int semester);
}
