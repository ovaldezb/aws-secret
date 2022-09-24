package com.ovaldez.asm.control;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ovaldez.asm.model.Employee;
import com.ovaldez.asm.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/employee")
public class Controller {
	
	@Autowired
	private EmployeeRepository employeRepo;

	@GetMapping
	public ResponseEntity<?> getAllEmployees(){
		return ResponseEntity.ok(employeRepo.findAll());
	}
	
	@GetMapping("/{empid}")
	public ResponseEntity<?> getEmployeeById(@PathVariable final int empid){
		Optional<Employee> empById = employeRepo.findById(empid);
		return ResponseEntity.ok(empById.get());
	}
	
	@PostMapping
	public ResponseEntity<?> addEmployee(@RequestBody final Employee employee){
		return ResponseEntity.ok(employeRepo.save(employee));
	}
}
