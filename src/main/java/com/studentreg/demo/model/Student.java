package com.studentreg.demo.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student extends BaseEntity {

    private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message="Name should not be empty")
    @Column
    private String name;

	@NotNull(message="Age should not be empty")
	@Min(value = 1, message = "Age should be greater than 0")
	@Column
	private Integer age;

	@NotNull(message="Gender should not be empty")
	@Enumerated(EnumType.STRING)
	@Column
	private Gender gender;

	@NotNull(message = "Birthday should not be empty")
	@Past(message = "Birthday should be in the past")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column
    private Date birthday;

	@NotEmpty(message="Address should not be empty")
    @Column
    private String address;

	@ToString.Exclude
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="course_id")
	Course course;
}
