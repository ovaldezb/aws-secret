package com.ovaldez.asm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ovaldez.asm.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
