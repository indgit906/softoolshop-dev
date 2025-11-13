package com.softoolshop.adminpanel.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.softoolshop.adminpanel.service.UpiQrGenerator;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/images")
public class ImageController {

	private final String IMG_DIR = "C:"+File.separator+"upload"+File.separator+"images";
	// upload\images\products
	private final String PROD_IMG_DIR = "C:"+File.separator+"upload"+File.separator+"images"+File.separator+"products";
	//private final String IMG_DIR = "/home/uts_sgold2025";
	private final String FILE_NAME = "QR_IMG.jpg";
	
    @GetMapping("qrcode")
    public ResponseEntity<Resource> getImage() {
        try {
        	
            Path filePath = Paths.get(IMG_DIR).resolve(FILE_NAME).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // or IMAGE_PNG depending on file
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("qrcode/{amt}")
    public ResponseEntity<Resource> generateQrcode(@PathVariable("amt") double strAmt) {
        try {
        	String IMG_UPI_QR =  "upi_qr_"+strAmt+".png";
        	String UPI_ID = "9901534356-2@ibl";
        	String STORE_NM = "Softoolshop";
        	
        	UpiQrGenerator.generateUpiQr(UPI_ID, STORE_NM, strAmt, "Order", IMG_UPI_QR);
        	
        	
            Path filePath = Paths.get(IMG_DIR).resolve(IMG_UPI_QR).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // or IMAGE_PNG depending on file
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
    }
    
    @GetMapping("product/{imgFileName:.+}")
    public ResponseEntity<Resource> getProductImage(@PathVariable String imgFileName) {
    	//System.out.println("imgFileName "+imgFileName);
        try {
            Path filePath = Paths.get(PROD_IMG_DIR).resolve(imgFileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            // Detect the content type
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (MalformedURLException ex) {
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            return ResponseEntity.status(500).build();
        }
    }
    
    @PostMapping("/products/upload")
    public ResponseEntity<Map<String, String>> uploadProductImage(@RequestParam("image") MultipartFile imageFile) {
        Map<String, String> response = new HashMap<>();

        // Validate empty file
        if (imageFile.isEmpty()) {
            response.put("error", "No file uploaded");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            // Ensure directory exists
            Path directoryPath = Paths.get(PROD_IMG_DIR);
            Files.createDirectories(directoryPath);

            // Get file extension safely
            String fileExtension = "";
            String originalFilename = imageFile.getOriginalFilename();
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            // Generate unique file name
            String uniqueFilename = UUID.randomUUID() + fileExtension;

            // Save file
            Path filePath = directoryPath.resolve(uniqueFilename);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Prepare response
            response.put("imageName", uniqueFilename);
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            response.put("error", "Failed to store file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
