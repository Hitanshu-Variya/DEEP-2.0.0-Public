package in.ac.daiict.deep.service.impl;

import in.ac.daiict.deep.service.RegistrationTaskManager;
import in.ac.daiict.deep.service.SystemStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;

@Service
public class RegistrationTaskManagerImpl implements RegistrationTaskManager {
    private final TaskScheduler taskScheduler;
    private ScheduledFuture<?> activeRegistrationTask;
    private SystemStatusService systemStatusService;
    private LocalDate closingDate;

    @Autowired
    @Lazy
    public RegistrationTaskManagerImpl(TaskScheduler taskScheduler, SystemStatusService systemStatusService) {
        this.taskScheduler = taskScheduler;
        this.systemStatusService = systemStatusService;
    }

    @Override
    public void updateCloseRegistrationDate(LocalDate closingDate) {
        this.closingDate=closingDate;
    }

    @Override
    public void startRegistration() {
        if(activeRegistrationTask!=null && !activeRegistrationTask.isCancelled()) return;

        //debug
        System.out.println("Starting the Registration!");
        activeRegistrationTask=taskScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Starting the task at: "+LocalDate.now()+" AND Closing Date is: "+closingDate);
                if(LocalDate.now().isAfter(closingDate)){
                    closeRegistration();
                }
            }
        }, Duration.ofSeconds(30));

//        activeRegistrationTask=taskScheduler.schedule(() -> {
//            if(LocalDate.now().isAfter(closingDate)){
//                closeRegistration();
//            }
//        }, new CronTrigger("0 1 0 * * *", TimeZone.getTimeZone("Asia/Kolkata")));
    }

    @Override
    public void closeRegistration() {
        if(activeRegistrationTask!=null && !activeRegistrationTask.isCancelled()){
            activeRegistrationTask.cancel(false);
        }
        activeRegistrationTask=null;
        closingDate=null;
        systemStatusService.autoCloseRegistration();
    }
}
