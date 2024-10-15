package com.shadi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shadi.entity.UserLifeStyleAndEducation;

@Repository
public interface UserLifeStyleAndEducationRepo extends JpaRepository<UserLifeStyleAndEducation, Long>{

}
