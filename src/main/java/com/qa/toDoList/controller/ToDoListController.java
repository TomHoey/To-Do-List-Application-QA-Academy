package com.qa.toDoList.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.toDoList.data.models.ToDoList;
import com.qa.toDoList.dto.ToDoListDTO;
import com.qa.toDoList.service.ToDoListService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/list")
public class ToDoListController {
	
	private ToDoListService tdlService;
	
	@Autowired
	public ToDoListController(ToDoListService tdlService) throws Exception {
		this.tdlService = tdlService;
	}
	
	@GetMapping
	public ResponseEntity<List<ToDoListDTO>> getAllLists() {
		List<ToDoListDTO> data = tdlService.listAllLists();
		
		return new ResponseEntity<List<ToDoListDTO>>(data, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ToDoListDTO> createList(@Valid @RequestBody ToDoList toDoList) {
		ToDoListDTO newList = tdlService.createList(toDoList);
		
		HttpHeaders header = new HttpHeaders();
		header.add("Location", String.valueOf(newList.getToDoID()));
		
		return new ResponseEntity<ToDoListDTO>(newList, header, HttpStatus.CREATED);
	}
	
	@PutMapping("/{toDoID}")
	public ResponseEntity<ToDoListDTO> updateList(@PathVariable("toDoID") int toDoID, 
			@RequestBody ToDoList toDoList) {
		ToDoListDTO updateList = tdlService.updateList(toDoID, toDoList);
		
		return new ResponseEntity<ToDoListDTO>(updateList, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{toDoID}")
	public ResponseEntity<Boolean> deleteList(@PathVariable("toDoID") int toDoID) {
		return new ResponseEntity<Boolean>(tdlService.deleteList(toDoID), HttpStatus.NO_CONTENT);
	}

}
