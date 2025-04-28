package com.auth.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//here I use lombok 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String firstName;
	private String lastName;
	
	@Column(unique=true, nullable=false)
	private String email;
	private String phone;
	private String password;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private UserRole role;
}
