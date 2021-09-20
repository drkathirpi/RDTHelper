package com.rdthelper.rdthelper.Repositories;

import com.rdthelper.rdthelper.Service.UploadInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class FileRepository {

    @Autowired
    UploadInterface uploadService;

    public java.io.File uploadFile(MultipartFile file){
        return uploadService.save(file);
    }

    public void deleteAll(){
        uploadService.deleteAll();
}
}
