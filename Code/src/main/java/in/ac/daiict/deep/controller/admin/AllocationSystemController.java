package in.ac.daiict.deep.controller.admin;

import in.ac.daiict.deep.constant.endpoints.AdminEndpoint;
import in.ac.daiict.deep.constant.response.ResponseStatus;
import in.ac.daiict.deep.constant.status.RegistrationStatusEnum;
import in.ac.daiict.deep.constant.template.AdminTemplate;
import in.ac.daiict.deep.dto.AllocationReqFilterDto;
import in.ac.daiict.deep.entity.AllocationStatus;
import in.ac.daiict.deep.service.AllocationStatusService;
import in.ac.daiict.deep.service.StudentService;
import in.ac.daiict.deep.dto.ResponseDto;
import in.ac.daiict.deep.service.SystemStatusService;
import in.ac.daiict.deep.util.allocation.AllocationSystem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Controller
@AllArgsConstructor
public class AllocationSystemController {
    private AllocationSystem allocationSystem;
    private StudentService studentService;
    private SystemStatusService systemStatusService;
    private AllocationStatusService allocationStatusService;

    @GetMapping(AdminEndpoint.RUN_ALLOCATION)
    public String renderRunAllocationPage(Model model){
        CompletableFuture<Void> statusFetchFuture=CompletableFuture.supplyAsync(() -> systemStatusService.fetchRegistrationStatus())
                .thenAccept(registrationStatus -> model.addAttribute("registrationStatus",registrationStatus));

        // Logic to send the allocation results status.
//        CompletableFuture<Void> allStatusFetchFuture=CompletableFuture.supplyAsync(() -> allocationStatusService.fetchAll())
//                .thenAccept(allocationStatusDtoList -> model.addAttribute("allocationStatus",allocationStatusDtoList));
//        CompletableFuture<Void> resultStatusFetchFuture=CompletableFuture.supplyAsync(() -> systemStatusService.fetchResultStatus())
//                        .thenAccept(resultStatus -> model.addAttribute("resultStatus",resultStatus));

//        CompletableFuture.allOf(statusFetchFuture,allStatusFetchFuture,resultStatusFetchFuture).join();

        model.addAttribute("executionFilter",new ArrayList<AllocationReqFilterDto>());
        return AdminTemplate.RUN_ALLOCATION_PAGE;
    }
//    @GetMapping(AdminEndpoint.RUN_ALLOCATION)
//    public String renderRunAllocationPage(Model model){
//        CompletableFuture<Void> statusFetchFuture=CompletableFuture.supplyAsync(() -> systemStatusService.fetchRegistrationStatus())
//                .thenAccept(registrationStatus -> model.addAttribute("registrationStatus",registrationStatus));
//        CompletableFuture<Void> allStatusFetchFuture=CompletableFuture.supplyAsync(() -> allocationStatusService.fetchAll())
//                .thenAccept(allocationStatusDtoList -> model.addAttribute("allocationStatus",allocationStatusDtoList));
//        CompletableFuture<Void> resultStatusFetchFuture=CompletableFuture.supplyAsync(() -> systemStatusService.fetchResultStatus())
//                .thenAccept(resultStatus -> model.addAttribute("resultStatus",resultStatus));
//
//        CompletableFuture.allOf(statusFetchFuture,allStatusFetchFuture,resultStatusFetchFuture).join();
//        return AdminTemplate.RUN_ALLOCATION_PAGE;
//    }

    @PostMapping(AdminEndpoint.EXECUTE_ALLOCATION)
    public String initiateAllocation(@ModelAttribute("executionFilter") List<AllocationReqFilterDto> executionFilter, @RequestParam("registrationStatus") String registrationStatus, RedirectAttributes redirectAttributes){
        CompletableFuture.runAsync(() -> {
            if (registrationStatus.equals(RegistrationStatusEnum.open.toString()))
                systemStatusService.updateOnClosingRegistration();
        });

//        Map<String,Long> unmetReqCnt=new HashMap<>();
//        ResponseDto allocationResponse=allocationSystem.initiateAllocation(semester,unmetReqCnt);
//
//        // Send the successfully allocated count.
//        long totalStudents=studentService.countAllStudents();
//        long allocatedCount=ResponseStatus.OK==allocationResponse.getStatus()?totalStudents-unmetReqCnt.size():0;
//
//        allocationStatusService.insertAllocationStatus(new AllocationStatus(semester,allocationResponse.getStatus(),(int)allocatedCount,unmetReqCnt.size()));
//
//        redirectAttributes.addFlashAttribute("semester",semester);
        return "redirect:"+AdminEndpoint.RUN_ALLOCATION;
    }

//    @PostMapping(AdminEndpoint.EXECUTE_ALLOCATION)
//    public String initiateAllocation(@PathVariable("semester") int semester, @RequestParam("registrationStatus") String registrationStatus, RedirectAttributes redirectAttributes){
//        CompletableFuture.runAsync(() -> {
//            if (registrationStatus.equals(RegistrationStatusEnum.open.toString()))
//                systemStatusService.updateOnClosingRegistration();
//        });
//
//        Map<String,Long> unmetReqCnt=new HashMap<>();
//        ResponseDto allocationResponse=allocationSystem.initiateAllocation(semester,unmetReqCnt);
//
//        // Send the successfully allocated count.
//        long totalStudents=studentService.countAllStudents();
//        long allocatedCount=ResponseStatus.OK==allocationResponse.getStatus()?totalStudents-unmetReqCnt.size():0;
//
//        allocationStatusService.insertAllocationStatus(new AllocationStatus(semester,allocationResponse.getStatus(),(int)allocatedCount,unmetReqCnt.size()));
//
//        redirectAttributes.addFlashAttribute("semester",semester);
//        return "redirect:"+AdminEndpoint.RUN_ALLOCATION;
//    }
}
