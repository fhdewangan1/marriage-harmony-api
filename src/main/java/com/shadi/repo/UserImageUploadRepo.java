package com.shadi.repo;

import com.shadi.entity.UserImageUploadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserImageUploadRepo extends JpaRepository<UserImageUploadEntity, Long> {
    List<UserImageUploadEntity> findByMobileNumber(String mobileNumber);
}
