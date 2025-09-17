package in.ac.daiict.deep.init;

import in.ac.daiict.deep.constant.status.RegistrationStatusEnum;
import in.ac.daiict.deep.service.RegistrationTaskManager;
import in.ac.daiict.deep.service.SystemStatusService;
import in.ac.daiict.deep.util.status.RegistrationCloseDate;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class RegistrationJobBootstrap implements ApplicationRunner {
    SystemStatusService systemStatusService;
    RegistrationTaskManager registrationTaskManager;
    @Override
    public void run(ApplicationArguments args) {
        String regStatus=systemStatusService.fetchRegistrationStatus();
        if(regStatus.equals(RegistrationStatusEnum.open.toString())) {
            RegistrationCloseDate registrationCloseDate=systemStatusService.fetchRegistrationCloseDate();
            if(registrationCloseDate.getCloseDate().isAfter(LocalDate.now())){
                registrationTaskManager.updateCloseRegistrationDate(registrationCloseDate.getCloseDate());
                registrationTaskManager.startRegistration();
            }
            else systemStatusService.updateOnClosingRegistration();
        }
    }
}