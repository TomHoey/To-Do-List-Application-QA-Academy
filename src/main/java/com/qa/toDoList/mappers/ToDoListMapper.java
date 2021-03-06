package com.qa.toDoList.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qa.toDoList.data.models.ToDoList;
import com.qa.toDoList.dto.ToDoListDTO;
	
	@Component
	public class ToDoListMapper {
		
		private ModelMapper modelMapper;
		
		@Autowired
		public ToDoListMapper(ModelMapper modelMapper) {
			this.modelMapper = modelMapper;
		}
		
		public ToDoListDTO mapToDTO(ToDoList toDoList) {
			return this.modelMapper.map(toDoList, ToDoListDTO.class);
		}
		
		public ToDoList mapToToDoList(ToDoListDTO toDoListDTO) {
			return this.modelMapper.map(toDoListDTO, ToDoList.class);
		}
		
		

}
