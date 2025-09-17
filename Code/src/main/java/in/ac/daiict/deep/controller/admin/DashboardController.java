package in.ac.daiict.deep.controller.admin;

import in.ac.daiict.deep.constant.endpoints.AdminEndpoint;
import in.ac.daiict.deep.constant.status.RegistrationStatusEnum;
import in.ac.daiict.deep.constant.status.ResultStatusEnum;
import in.ac.daiict.deep.dto.SystemStatusDto;
import in.ac.daiict.deep.service.SystemStatusService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
public class DashboardController {

    private SystemStatusService systemStatusService;

    @PostMapping(AdminEndpoint.BEGIN_COLLECTION)
    public String openRegistration(@RequestParam("close-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate closeDate){
        SystemStatusDto systemStatusDto=new SystemStatusDto(closeDate);
        systemStatusService.updateOnOpeningRegistration(systemStatusDto);
        return "redirect:"+AdminEndpoint.DASHBOARD;
    }

    @PostMapping(AdminEndpoint.EXTEND_COLLECTION_PERIOD)
    public String extendRegistrationPeriod(@RequestParam("close-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate closeDate){
        SystemStatusDto systemStatusDto=new SystemStatusDto(closeDate);
        systemStatusService.updateOnExtendingRegistrationPeriod(systemStatusDto);
        return "redirect:"+AdminEndpoint.DASHBOARD;
    }

    @PostMapping(AdminEndpoint.END_COLLECTION)
    public String closeRegistration(){
        systemStatusService.updateOnClosingRegistration();
        return "redirect:"+AdminEndpoint.DASHBOARD;
    }

    @PostMapping(AdminEndpoint.DECLARE_RESULTS)
    public String declareResults(){
        SystemStatusDto systemStatusDto=new SystemStatusDto(RegistrationStatusEnum.never, ResultStatusEnum.declared);
        systemStatusService.updateOnDeclaringResults(systemStatusDto);
        return "redirect:"+AdminEndpoint.DASHBOARD;
    }
}