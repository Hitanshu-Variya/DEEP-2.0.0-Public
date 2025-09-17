package in.ac.daiict.deep.controller.student;

import in.ac.daiict.deep.constant.endpoints.StudentEndpoint;
import in.ac.daiict.deep.constant.template.StudentTemplate;
import in.ac.daiict.deep.dto.SystemStatusDto;
import in.ac.daiict.deep.service.SystemStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HomePageController {

    private SystemStatusService systemStatusService;

    @GetMapping(StudentEndpoint.HOME_PAGE)
    public String renderStudentHomePage(Model model){
        SystemStatusDto systemStatusDto=systemStatusService.fetchAllStatus();
        model.addAttribute("registrationStatus",systemStatusDto.getRegistrationStatus().getStatusValue());
        model.addAttribute("resultStatus",systemStatusDto.getResultStatus().getStatusValue());
        if(systemStatusDto.getRegistrationCloseDate()!=null) model.addAttribute("registrationCloseDate",systemStatusDto.getRegistrationCloseDate().getCloseDate());
        return StudentTemplate.HOME_PAGE;
    }

    @GetMapping(StudentEndpoint.FAQ)
    public String renderFaqPage(){
        return StudentTemplate.FAQ_PAGE;
    }

    @GetMapping(StudentEndpoint.GUIDELINES)
    public String renderGuidelinesPage(){
        return StudentTemplate.GUIDELINES_PAGE;
    }
}
