package com.qa.toDoList.data;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.qa.toDoList.dto.TaskDTO;
import com.qa.toDoList.dto.ToDoListDTO;

import nl.jqno.equalsverifier.EqualsVerifier;

public class ToDoListDTOUnitTest {
	
	@Test
	void testEquals() {
		EqualsVerifier.simple().forClass(ToDoListDTO.class).verify();
	}
	
	@Test
	void ConstructorTest() {
		ToDoListDTO newDTOTest = new ToDoListDTO(0, null, new ArrayList<TaskDTO>());
		newDTOTest.toString();
	}

}
