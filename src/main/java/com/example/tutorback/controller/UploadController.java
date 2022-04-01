package com.example.tutorback.controller;

import com.example.tutorback.utils.getNameFromOriginalFilename;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author Aky
 * @version 1.0
 * @date 2022/3/30 14:00
 */
@Controller
public class UploadController {

/*    @GetMapping("/toUpload")
    public String toUpload(){
        return "upload";
    }

    @ResponseBody                                       //使返回的是文字而不是页面
    @RequestMapping(value="/uploadService")*/

    @PostMapping("/uploadService")
    public @ResponseBody
    boolean upload(MultipartFile headImage) throws IOException {
        if (!headImage.isEmpty()) {
            String originalFilename = headImage.getOriginalFilename();
            assert originalFilename != null;
            String username = getNameFromOriginalFilename.getUsername(originalFilename);
            String filename = getNameFromOriginalFilename.getFilename(originalFilename);

            String path = "E:\\JAVA\\Project\\cache\\" + username + "\\" + "headImage\\" + filename;               //最终存储路径

            File file = new File(path);
            if (!file.getParentFile().exists()) {                                               //如果路径不存在，则创建
                file.getParentFile().mkdirs();
            }
            headImage.transferTo(file.toPath());
            System.out.println("upload picture successfully, filename is " + filename);
            return true;
        } else {
            return false;
        }
    }


    @PostMapping("/downloadService")
    public ResponseEntity<FileSystemResource> download(@RequestParam String headImageName) {
        if (headImageName == null || headImageName.isEmpty())
            return null;

        String username = getNameFromOriginalFilename.getUsername(headImageName);
        String filename = getNameFromOriginalFilename.getFilename(headImageName);
        String path = "E:\\JAVA\\Project\\cache\\" + username + "\\" + "headImage";               //最终存储路径
        File file = Paths.get(path).resolve(filename).toFile();
        if (file.exists() && file.canRead()) {
            System.out.println("download file , filename is " + filename);
            return ResponseEntity.ok()
                    .contentType(file.getName().contains(".jpg") ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG)
                    .body(new FileSystemResource(file));
        } else
            return null;
    }
}
