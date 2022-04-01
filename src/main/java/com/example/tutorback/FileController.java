package com.example.tutorback;

import com.example.tutorback.utils.getNameFromOriginalFilename;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author Aky
 * @version 1.0
 * @date 2022/3/27 17:35
 */


@Controller
public class FileController {
    @Value("${upload.file.path}")
    private String uploadPathStr;

    public FileController() {
    }

    @PostMapping("/upload")
    public @ResponseBody
    boolean upload(@RequestParam MultipartFile headImage, @RequestParam String filename) {
        if (headImage == null || headImage.isEmpty() || filename == null || filename.isEmpty())
            return false;
        //System.out.println("OriginalFilename = " + originalFilename);
        try (InputStream inputStream = headImage.getInputStream()) {
            Path uploadPath = Paths.get(uploadPathStr);
            if (!uploadPath.toFile().exists())
                uploadPath.toFile().mkdirs();
            Files.copy(inputStream, Paths.get(uploadPathStr).resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("upload file , filename is " + filename);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/download")
    public ResponseEntity<FileSystemResource> download(@RequestParam String filename) {
        if (filename == null || filename.isEmpty())
            return null;
        File file = Paths.get(uploadPathStr).resolve(filename).toFile();
        if (file.exists() && file.canRead()) {
            System.out.println("download file , filename is " + filename);
            return ResponseEntity.ok()
                    .contentType(file.getName().contains(".jpg") ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG)
                    .body(new FileSystemResource(file));
        } else
            return null;
    }
}

