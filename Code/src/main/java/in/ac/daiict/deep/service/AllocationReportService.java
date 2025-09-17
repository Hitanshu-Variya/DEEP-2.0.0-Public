package in.ac.daiict.deep.service;

import in.ac.daiict.deep.entity.AllocationReport;

public interface AllocationReportService {
    void insertReport(AllocationReport allocationReport);
    void deleteReport(AllocationReport allocationReport);
    AllocationReport fetchReport(String name, int semester);
}
