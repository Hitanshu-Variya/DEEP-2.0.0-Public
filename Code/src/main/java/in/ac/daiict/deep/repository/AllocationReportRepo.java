package in.ac.daiict.deep.repository;

import in.ac.daiict.deep.entity.AllocationReport;
import in.ac.daiict.deep.entity.compositekeys.AllocationReportPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllocationReportRepo extends JpaRepository<AllocationReport, AllocationReportPK> {
}
