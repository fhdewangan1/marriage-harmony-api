package com.shadi.repo;

import com.shadi.entity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserImageRepo extends JpaRepository<UserImage, Long> {
    List<UserImage> findByMobileNumber(String mobileNumber);
}
