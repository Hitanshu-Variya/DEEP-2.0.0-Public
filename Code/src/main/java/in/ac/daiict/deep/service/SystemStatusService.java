package in.ac.daiict.deep.service;

import in.ac.daiict.deep.dto.SystemStatusDto;
import in.ac.daiict.deep.util.status.RegistrationCloseDate;

import java.time.LocalDate;

public interface SystemStatusService {
    void updateOnOpeningRegistration(SystemStatusDto systemStatusDto);
    void updateOnExtendingRegistrationPeriod(SystemStatusDto systemStatusDto);
    void updateOnClosingRegistration();
    void autoCloseRegistration();
    void updateOnDeclaringResults(SystemStatusDto systemStatusDto);
    SystemStatusDto fetchAllStatus();
    String fetchRegistrationStatus();
    String fetchResultStatus();
    RegistrationCloseDate fetchRegistrationCloseDate();
}
