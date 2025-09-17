package in.ac.daiict.deep.service;


import java.io.File;

public interface InstanceNameService {
    String fetchLatestInstance();
    boolean checkIfNewInstanceExists(String newInstanceName);
    boolean insertNewInstance(String newInstanceName);
    void migrateInstances(File dir);
    void deleteInstance(String instanceName);
    boolean insertFromFile(File dir);
}
