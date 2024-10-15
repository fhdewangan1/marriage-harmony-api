package com.shadi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shadi.entity.UserPartnerPreferences;


public interface UserPartnerPreferencesRepo extends JpaRepository<UserPartnerPreferences, Long> {

}
