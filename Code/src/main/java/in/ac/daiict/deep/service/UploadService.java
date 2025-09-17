package in.ac.daiict.deep.service;

import in.ac.daiict.deep.entity.Upload;

import java.util.List;

public interface UploadService {
    void insertAll(List<Upload> uploads);
    Upload findFile(String name);
    void deleteAll();
}
