package com.shadi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shadi.entity.UserFamilyDetails;

@Repository
public interface UserFamilyDetailsRepo extends JpaRepository<UserFamilyDetails, Long> {

}
