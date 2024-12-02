package com.infosys.Wheelwise.repo;

import com.infosys.Wheelwise.Model.ProfilePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfilePhotoRepository extends JpaRepository<ProfilePhoto, Long> {
    Optional<ProfilePhoto> findByUserId(Long userId);
}
