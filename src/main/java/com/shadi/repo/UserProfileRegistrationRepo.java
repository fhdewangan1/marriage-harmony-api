package com.shadi.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shadi.profile.entity.UserRegistrationProfile;

@Repository
public interface UserProfileRegistrationRepo extends JpaRepository<UserRegistrationProfile, String> {

	Page<UserRegistrationProfile> findAll(Pageable pageable);

	Page<UserRegistrationProfile> findAllByGender(String gender, Pageable pageable);

	@Query(name = "SELECT `mobile_number`, `age`, `community`, `confirm_password`, `created_time`, `dob`, `first_name`, `gender`, `lang_known`, `last_name`, `password`, `religion`, `residence`, `extension`, `updated_time` FROM `iw-shadi`.`user_registration_profile`;", nativeQuery = true)
	UserRegistrationProfile findByMobileNumber(String mobileNumber);

	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("UPDATE UserRegistrationProfile fud  SET fud.password=:password WHERE fud.mobileNumber=:mobileNumber")
	void updateFinoUserPassword(@Param("password") String password, @Param("mobileNumber") String mobileNumber);
}
