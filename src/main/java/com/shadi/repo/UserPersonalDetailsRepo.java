package com.shadi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shadi.entity.UserPersonalDetails;

@Repository
public interface UserPersonalDetailsRepo extends JpaRepository<UserPersonalDetails, Long> {

}
