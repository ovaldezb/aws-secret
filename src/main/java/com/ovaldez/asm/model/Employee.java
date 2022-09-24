package com.ovaldez.asm.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "Employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

	@Id
	@GeneratedValue
	private int id;
	private String nombre;
	private String puesto;
	private double salario;
	private String area;
}
