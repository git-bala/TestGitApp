package com.rest.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Client implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private long id;
	
	@NotBlank(message = "Field is empty")
	@Column(nullable = false, length = 150)
	private String name;
	
	@NotBlank(message = "Field is empty")
	@Column(nullable = false, length = 255)
	private String address;
	
	@NotBlank(message = "Field is empty")
	@Column(nullable = false, length = 255)
	@Email(message = "Invalid email")
	private String email;

}
