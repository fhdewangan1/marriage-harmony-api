package com.shadi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shadi.entity.SearchDetails;

@Repository
public interface SearchDetailsRepo extends JpaRepository<SearchDetails, String> {

	SearchDetails findByMotherTongueAndGenderAndReligion(String motherTongue, String gender, String religion);

}
