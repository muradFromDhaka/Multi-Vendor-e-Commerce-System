 package com.abc.multiVendorEProject.service;

 import jakarta.annotation.PostConstruct;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.stereotype.Service;
 import org.springframework.web.multipart.MultipartFile;

 import java.io.IOException;
 import java.nio.file.Files;
 import java.nio.file.Path;
 import java.nio.file.Paths;
 import java.nio.file.StandardCopyOption;
 import java.util.UUID;

 @Service
 public class FileStorageService {

     @Value("${file.upload-dir}")
     private String uploadDir;

     private Path uploadPath;

     @PostConstruct
     public void init() throws IOException {
         uploadPath = Paths.get(uploadDir);

         if (!Files.exists(uploadPath)) {
             Files.createDirectories(uploadPath);
         }
     }


     public String saveFile(MultipartFile file) {
         try {
             String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
             Path targetPath = uploadPath.resolve(fileName);
             Files.copy(file.getInputStream(),targetPath,StandardCopyOption.REPLACE_EXISTING);
             return targetPath.toString().replace("\\", "/");
         } catch (IOException e) {
             throw new RuntimeException(
                     "Failed to save file: " + file.getOriginalFilename(), e);
         }
     }

     public void deleteFile(String fileName) {
         try {
             Path filePath = uploadPath.resolve(fileName);
             Files.deleteIfExists(filePath);
         } catch (IOException e) {
             throw new RuntimeException("Failed to delete file: " + fileName, e);
         }
     }


     public void deleteFileByPath(String imagePath) {
         try {
             Files.deleteIfExists(Paths.get(imagePath));
         } catch (IOException e) {
             throw new RuntimeException("Failed to delete file: " + imagePath, e);
         }
     }

 }
