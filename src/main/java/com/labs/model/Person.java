package com.labs.model;

import com.labs.xls.ExcelRow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

@Getter
@ToString
@AllArgsConstructor
public class Person implements ExcelRow {
	
	private final Long id;
	private final String firstName;
	private final String lastName;
	private final String email;
	private final String gender;
	private final LocalDate birthDate;
	private final String streetAddress;
	private final String state;
	private final String country;
	private final String companyName;
	
	@Override
	public ArrayList<String> getRowAsString() {
		return new ArrayList<>(Arrays.asList(id.toString(), firstName, lastName, email, gender,
		                                     birthDate.format(DateTimeFormatter.ISO_DATE), streetAddress, state,
		                                     country, companyName));
	}
	
	public static class PersonRecord {
		
		public static final String[] headers = { "id",
		                                         "first name",
		                                         "last name",
		                                         "email",
		                                         "gender",
		                                         "birth date",
		                                         "street address",
		                                         "state",
		                                         "country",
		                                         "company name" };
	}
}
