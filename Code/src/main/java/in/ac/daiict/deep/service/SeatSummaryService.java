package in.ac.daiict.deep.service;

import in.ac.daiict.deep.entity.SeatSummary;

import java.util.List;

public interface SeatSummaryService {
    void insertAll(List<SeatSummary> seatSummaryList);
    void deleteAll();
}
