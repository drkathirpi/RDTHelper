package com.rdthelper.rdthelper.Api;

import com.rdthelper.rdthelper.Models.RDTUpload;
import com.rdthelper.rdthelper.Repositories.FileRepository;
import com.rdthelper.rdthelper.Repositories.TorrentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FileApi {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    TorrentsRepository torrentsRepository;

    @PostMapping("/api/torrent/upload")
    public List<RDTUpload> uploadFile(@RequestParam("file") MultipartFile... files){
        List<RDTUpload> rdtFiles = new ArrayList<>();
        try {
            for (MultipartFile file : files){
                //java.io.File result = fileRepository.uploadFile(file);
                rdtFiles.add(torrentsRepository.addTorrent(file));
                //fileRepository.deleteAll();
            }

            return rdtFiles;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /*@GetMapping("/api/files")
    public ResponseEntity<List<File>> getListFiles() {
        List<File> fileInfos = uploadService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FileApi.class, "getFile", path.getFileName().toString()).build().toString();

            return new File(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/api/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = uploadService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }*/
}
