package in.ac.daiict.deep.service.impl;

import in.ac.daiict.deep.entity.Upload;
import in.ac.daiict.deep.repository.UploadRepo;
import in.ac.daiict.deep.service.UploadService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UploadServiceImpl implements UploadService {
    private UploadRepo uploadRepo;

    @Override
    public void insertAll(List<Upload> uploads) {
        uploadRepo.saveAllAndFlush(uploads);
    }

    @Override
    public Upload findFile(String name) {
        return uploadRepo.findById(name).orElse(null);
    }

    @Override
    public void deleteAll() {
        uploadRepo.deleteAll();
    }
}
