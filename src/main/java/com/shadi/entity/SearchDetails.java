package com.shadi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SearchDetails {
	@Id
	private String sId;
	private String gender;
	private int fromAge;
	private int toAge;
	private String religion;
	private String motherTongue;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		SearchDetails that = (SearchDetails) o;

		if (!sId.equals(that.sId))
			return false;
		if (!gender.equals(that.gender))
			return false;
		if (fromAge != that.fromAge)
			return false;
		if (toAge != that.toAge)
			return false;
		if (!religion.equals(that.religion))
			return false;
		return motherTongue.equals(that.motherTongue);
	}

	@Override
	public int hashCode() {
		int result = sId.hashCode();
		result = 31 * result + gender.hashCode();
		result = 31 * result + fromAge;
		result = 31 * result + toAge;
		result = 31 * result + religion.hashCode();
		result = 31 * result + motherTongue.hashCode();
		return result;
	}
}
