package in.ac.daiict.deep.repository;

import in.ac.daiict.deep.entity.AllocationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllocationStatusRepo extends JpaRepository<AllocationStatus,Integer> {
}
