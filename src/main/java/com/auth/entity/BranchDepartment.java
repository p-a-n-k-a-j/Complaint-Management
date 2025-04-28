package com.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="branches_departments")
public class BranchDepartment {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
		
		String name;
		
		//Getter and Setters are created by lombok
	
}
