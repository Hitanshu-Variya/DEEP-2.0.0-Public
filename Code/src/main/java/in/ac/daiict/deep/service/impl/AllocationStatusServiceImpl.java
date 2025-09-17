package in.ac.daiict.deep.service.impl;

import in.ac.daiict.deep.dto.AllocationStatusDto;
import in.ac.daiict.deep.entity.AllocationStatus;
import in.ac.daiict.deep.repository.AllocationStatusRepo;
import in.ac.daiict.deep.service.AllocationStatusService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AllocationStatusServiceImpl implements AllocationStatusService {

    private AllocationStatusRepo allocationStatusRepo;
    private ModelMapper modelMapper;

    @Override
    public void insertAllocationStatus(AllocationStatus allocationStatus) {
        allocationStatusRepo.save(allocationStatus);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AllocationStatusDto> fetchAll() {
        List<AllocationStatus> allocationStatusList=allocationStatusRepo.findAll();
        if(allocationStatusList.isEmpty()) return null;
        return modelMapper.map(allocationStatusList,new TypeToken<List<AllocationStatusDto>>(){}.getType());
    }

    @Override
    public boolean checkIfExists(int semester) {
        return allocationStatusRepo.existsById(semester);
    }
}
