package com.infosys.Wheelwise.Controller;


import com.infosys.Wheelwise.Services.ProfilePhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/profile-photo")
public class ProfilePhotoController {

    @Autowired
    private ProfilePhotoService profilePhotoService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadProfilePhoto(@RequestParam Long userId, @RequestParam MultipartFile file) {
        try {
            profilePhotoService.saveProfilePhoto(userId, file);
            return ResponseEntity.ok("Profile photo uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading profile photo: " + e.getMessage());
        }
    }

    @GetMapping("/view")
    public ResponseEntity<byte[]> viewProfilePhoto(@RequestParam Long userId) {
        return profilePhotoService.getProfilePhotoByUserId(userId)
                .map(profilePhoto -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                        .body(profilePhoto.getPhoto()))
                .orElse(ResponseEntity.notFound().build());
    }

}

