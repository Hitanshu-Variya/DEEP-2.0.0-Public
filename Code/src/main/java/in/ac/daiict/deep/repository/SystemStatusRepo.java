package in.ac.daiict.deep.repository;

import in.ac.daiict.deep.entity.SystemStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemStatusRepo extends JpaRepository<SystemStatus,String> {
}
