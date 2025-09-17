package in.ac.daiict.deep.dto;

import in.ac.daiict.deep.constant.status.RegistrationStatusEnum;
import in.ac.daiict.deep.constant.status.ResultStatusEnum;
import in.ac.daiict.deep.constant.status.UpdateInstanceStatusEnum;
import in.ac.daiict.deep.util.status.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class SystemStatusDto {
    private RegistrationStatus registrationStatus=null;
    private UpdateInstanceStatus updateInstanceStatus=null;
    private RegistrationCloseDate registrationCloseDate=null;
    private ResultStatus resultStatus=null;

    public SystemStatusDto(RegistrationStatusEnum registrationStatusValue, LocalDate closingDate, UpdateInstanceStatusEnum updateInstanceStatusValue){
        this.registrationStatus=new RegistrationStatus(registrationStatusValue);
        this.registrationCloseDate=new RegistrationCloseDate(closingDate);
        this.updateInstanceStatus=new UpdateInstanceStatus(updateInstanceStatusValue);
    }

    public SystemStatusDto(RegistrationStatusEnum registrationStatusValue, ResultStatusEnum resultStatusValue){
        this.registrationStatus=new RegistrationStatus(registrationStatusValue);
        this.resultStatus=new ResultStatus(resultStatusValue);
    }

    public SystemStatusDto(LocalDate closingDate){
        this.registrationCloseDate=new RegistrationCloseDate(closingDate);
    }

    public SystemStatusDto(RegistrationStatusEnum registrationStatusValue){
        this.registrationStatus=new RegistrationStatus(registrationStatusValue);
    }

    public void setRegistrationStatus(String statusValue){
        this.registrationStatus=new RegistrationStatus(RegistrationStatusEnum.valueOf(statusValue));
    }

    public void setUpdateInstanceStatus(String statusValue){
        this.updateInstanceStatus=new UpdateInstanceStatus(UpdateInstanceStatusEnum.valueOf(statusValue));
    }

    public void setRegistrationCloseDate(String closeDate){
        this.registrationCloseDate=new RegistrationCloseDate(LocalDate.parse(closeDate));
    }

    public void setResultStatus(String statusValue){
        this.resultStatus=new ResultStatus(ResultStatusEnum.valueOf(statusValue));
    }
}