package com.shadi.records;

import java.time.LocalDate;

public record ProfileRecords(
    String mobileNumber,
    String firstName,
    String lastName,
    int age,
    String gender, // gender is a String, not List<String>
    String langKnown, // langKnown is a String in the constructor
    String religion,
    String community,
    LocalDate dob,
    String residence
//    UserFamilyDetails userFamilyDetails
) { }

