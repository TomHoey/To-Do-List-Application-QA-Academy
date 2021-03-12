package com.qa.toDoList.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.toDoList.data.models.Customer;
import com.qa.toDoList.dto.CustomerDTO;
import com.qa.toDoList.service.CustomerService;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@PostMapping
	public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody Customer customer) {
		
		CustomerDTO newCust = customerService.createCustomer(customer);
		
		HttpHeaders header = new HttpHeaders();
		header.add("Location", String.valueOf(newCust.getCid()));
		
		return new ResponseEntity<CustomerDTO>(newCust, header, HttpStatus.CREATED);
	}

}
