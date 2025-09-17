package in.ac.daiict.deep.repository;

import in.ac.daiict.deep.constant.database.DBConstants;
import in.ac.daiict.deep.entity.SeatSummary;
import in.ac.daiict.deep.entity.compositekeys.SeatSummaryPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SeatSummaryRepo extends JpaRepository<SeatSummary, SeatSummaryPK> {
    @Override
    @Modifying
    @Query(value = "DELETE FROM "+DBConstants.WORKING_SCHEMA+"."+ DBConstants.SEAT_SUMMARY_TABLE,nativeQuery = true)
    void deleteAll();
}
