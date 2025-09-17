package in.ac.daiict.deep.service;

import in.ac.daiict.deep.dto.InstituteReqDto;
import in.ac.daiict.deep.entity.InstituteReq;
import in.ac.daiict.deep.dto.ResponseDto;

import java.util.List;

public interface InstituteReqService {
    ResponseDto insertAll(byte[] instituteData);
    List<InstituteReq> fetchAllInstituteReqs();
    List<InstituteReqDto> fetchAllInstituteReqDtos();
    List<InstituteReq> fetchInstituteReqBySemester(int semester);
    void deleteAll();
    List<InstituteReqDto> findInstituteReq(String program, int semester);
}
