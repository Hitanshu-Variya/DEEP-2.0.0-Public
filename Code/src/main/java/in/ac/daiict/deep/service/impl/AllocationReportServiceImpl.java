package in.ac.daiict.deep.service.impl;

import in.ac.daiict.deep.entity.AllocationReport;
import in.ac.daiict.deep.entity.compositekeys.AllocationReportPK;
import in.ac.daiict.deep.repository.AllocationReportRepo;
import in.ac.daiict.deep.service.AllocationReportService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AllocationReportServiceImpl implements AllocationReportService {

    private AllocationReportRepo allocationReportRepo;

    @Override
    @Transactional
    public void insertReport(AllocationReport allocationReport) {
        deleteReport(allocationReport);
        allocationReportRepo.save(allocationReport);
    }

    @Override
    public void deleteReport(AllocationReport allocationReport) {
        allocationReportRepo.delete(allocationReport);
    }

    @Override
    public AllocationReport fetchReport(String name, int semester) {
        return allocationReportRepo.findById(new AllocationReportPK(name,semester)).orElse(null);
    }
}
