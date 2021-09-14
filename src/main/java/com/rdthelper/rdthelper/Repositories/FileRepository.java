package com.rdthelper.rdthelper.Repositories;

import com.rdthelper.rdthelper.Models.File;
import com.rdthelper.rdthelper.Models.RDTUpload;
import com.rdthelper.rdthelper.Service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileRepository {

    @Autowired
    UploadService uploadService;

    public java.io.File uploadFile(MultipartFile file){
        return uploadService.save(file);
    }

public void deleteAll(){
        uploadService.deleteAll();
}
}
