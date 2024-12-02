package com.infosys.Wheelwise.Services;


import com.infosys.Wheelwise.Model.ProfilePhoto;
import com.infosys.Wheelwise.repo.ProfilePhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ProfilePhotoService {

    @Autowired
    private ProfilePhotoRepository profilePhotoRepository;

    public ProfilePhoto saveProfilePhoto(Long userId, MultipartFile file) throws IOException {
        ProfilePhoto profilePhoto = profilePhotoRepository.findByUserId(userId).orElse(new ProfilePhoto());
        profilePhoto.setUserId(userId);
        profilePhoto.setPhoto(file.getBytes());
        return profilePhotoRepository.save(profilePhoto);
    }

    public Optional<ProfilePhoto> getProfilePhotoByUserId(Long userId) {
        return profilePhotoRepository.findByUserId(userId);
    }
}
