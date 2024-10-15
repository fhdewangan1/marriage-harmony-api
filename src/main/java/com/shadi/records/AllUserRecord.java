package com.shadi.records;

import java.time.LocalDate;

import com.shadi.entity.UserFamilyDetails;
import com.shadi.entity.UserLifeStyleAndEducation;
import com.shadi.entity.UserPartnerPreferences;
import com.shadi.entity.UserPersonalDetails;

public record AllUserRecord(
	
    String mobileNumber,
    String firstName,
    String lastName,
    int age,
    String gender, 
    String langKnown,
    String religion,
    String community,
    LocalDate dob,
    String residence,
    String mailId,
    UserFamilyDetails userFamilyDetails,
    UserLifeStyleAndEducation userLifeStyleAndEducation,
    UserPersonalDetails userPersonalDetails,
    UserPartnerPreferences userPartnerPreferences
) { }
