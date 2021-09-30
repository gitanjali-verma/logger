package com.digitaldots.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
}
