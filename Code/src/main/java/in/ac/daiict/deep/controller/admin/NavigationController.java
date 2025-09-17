package in.ac.daiict.deep.controller.admin;

import in.ac.daiict.deep.constant.endpoints.AdminEndpoint;
import in.ac.daiict.deep.constant.template.AdminTemplate;
import in.ac.daiict.deep.dto.AdminDashboardReqDto;
import in.ac.daiict.deep.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class NavigationController {
    private SystemStatusService systemStatusService;
    private StudentService studentService;
    private AllocationStatusService allocationStatusService;

    @GetMapping(AdminEndpoint.DASHBOARD)
    public String renderDashboardPage(Model model){
        CompletableFuture<Void> fetchAllStatus =CompletableFuture.supplyAsync(() -> systemStatusService.fetchAllStatus())
                .thenAccept(systemStatusDto -> {
                    if(systemStatusDto.getRegistrationStatus()!=null) model.addAttribute("registrationStatus",systemStatusDto.getRegistrationStatus().getStatusValue());
                    if(systemStatusDto.getUpdateInstanceStatus()!=null) model.addAttribute("updateInstanceStatus",systemStatusDto.getUpdateInstanceStatus().getStatusValue());
                    if(systemStatusDto.getResultStatus()!=null) model.addAttribute("resultStatus",systemStatusDto.getResultStatus().getStatusValue());
                    if(systemStatusDto.getRegistrationCloseDate()!=null) model.addAttribute("registrationCloseDate",systemStatusDto.getRegistrationCloseDate().getCloseDate());
                });

        List<CompletableFuture<AdminDashboardReqDto>> collectDashboardReq=new ArrayList<>();
        for(int sem=5;sem<=8;sem++){
            int finalSem = sem;
            CompletableFuture<Long> fetchingNumberOfStudents=CompletableFuture.supplyAsync(() -> studentService.countBySemester(finalSem));
            CompletableFuture<Long> fetchingEnrolledStudentCount=CompletableFuture.supplyAsync(() -> studentService.countEnrolledStudents());
            CompletableFuture<Boolean> checkingExistence=CompletableFuture.supplyAsync(() -> allocationStatusService.checkIfExists(finalSem));
            collectDashboardReq.add(fetchingNumberOfStudents.thenCombine(fetchingEnrolledStudentCount, (totalStudents, enrolledStudents) -> new Long[]{totalStudents,enrolledStudents}).
                    thenCombine(checkingExistence, (firstTwoTasks, isExist) -> new AdminDashboardReqDto(finalSem,firstTwoTasks[0],firstTwoTasks[1],isExist))
            );
        }

        CompletableFuture<Void> fetchDashboardReqs = CompletableFuture.allOf(
                collectDashboardReq.toArray(new CompletableFuture[0])
        );

        CompletableFuture.allOf(fetchAllStatus, fetchDashboardReqs).join();
        List<AdminDashboardReqDto> adminDashboardReqDtoList=collectDashboardReq.stream().map(CompletableFuture::join).collect(Collectors.toList());

        model.addAttribute("dashboardRequirement",adminDashboardReqDtoList);
        return AdminTemplate.DASHBOARD_PAGE;
    }

    @GetMapping(AdminEndpoint.DOWNLOAD_REPORTS)
    public String renderDownloadReportsPage(){
        return AdminTemplate.DOWNLOAD_REPORTS_PAGE;
    }

    @GetMapping(AdminEndpoint.STUDENT_PREFERENCE)
    public String renderStudentPreferencesPage(){
        return AdminTemplate.STUDENTS_PREFERENCES_PAGE;
    }

    @GetMapping(AdminEndpoint.ALLOCATION_RESULTS)
    public String renderAllocationResultsPage(){
        return AdminTemplate.ALLOCATION_RESULTS_PAGE;
    }

    @GetMapping(AdminEndpoint.FAQ)
    public String renderFaqPage(){
        return AdminTemplate.FAQ_PAGE;
    }
}
