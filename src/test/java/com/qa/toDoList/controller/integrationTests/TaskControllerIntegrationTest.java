package com.qa.toDoList.controller.integrationTests;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.toDoList.controller.TaskController;
import com.qa.toDoList.data.models.Tasks;
import com.qa.toDoList.data.models.ToDoList;
import com.qa.toDoList.data.respository.TaskRepository;
import com.qa.toDoList.dto.TaskDTO;
import com.qa.toDoList.mappers.TaskMapper;
import com.qa.toDoList.service.TaskService;

@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:test-schema.sql", "classpath:test-data.sql" }, 
	 executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class TaskControllerIntegrationTest {
	
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private TaskController taskController;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private TaskRepository taskRepo;
	
	@Autowired
	private TaskMapper taskMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Tasks validTask = new Tasks(1, "Running", "Run 5km");
	private TaskDTO validTaskDTO = new TaskDTO(1, "Running", "Run 5km");

	private List<Tasks> validTasks = List.of(validTask);
	private List<TaskDTO> validTaskDTOs = List.of(validTaskDTO);
	
	private ToDoList validList= new ToDoList(1,"Exercise", validTasks);

	@Test
	void createTaskTest() throws Exception {
		Tasks taskSave = new Tasks("Sprinting", "100m");
		taskSave.setToDoList(validList);
		
		TaskDTO requiredTask = new TaskDTO(2, "Sprinting", "100m");
		MockHttpServletRequestBuilder mockRequest = 
				MockMvcRequestBuilders.request(HttpMethod.POST, "/task/1");
		mockRequest.accept(MediaType.APPLICATION_JSON);
		
		mockRequest.contentType(MediaType.APPLICATION_JSON); 
		mockRequest.content(objectMapper.writeValueAsString(taskSave));
		mockRequest.accept(MediaType.APPLICATION_JSON);
		ResultMatcher statusMatcher = MockMvcResultMatchers.status().isCreated();
		ResultMatcher contentMatcher = MockMvcResultMatchers.content()
				.json(objectMapper.writeValueAsString(requiredTask));
		ResultMatcher headerMatcher = MockMvcResultMatchers.header().string("Location", "2");
		mvc.perform(mockRequest)
		   .andExpect(statusMatcher)
		   .andExpect(contentMatcher)
		   .andExpect(headerMatcher);
	}
	
	
	@Test
	void readTaskByID () throws Exception {
		
		MockHttpServletRequestBuilder mockRequest = 
				MockMvcRequestBuilders.request(HttpMethod.GET, "/task/1");
		mockRequest.accept(MediaType.APPLICATION_JSON);
		
		ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();
		ResultMatcher contentMatcher = MockMvcResultMatchers.content()
				.json(objectMapper.writeValueAsString(validTaskDTOs));
		
		mvc.perform(mockRequest)
		   .andExpect(statusMatcher)
		   .andExpect(contentMatcher);
	}
	
	@Test
	void updateTaskTest() throws Exception {
		Tasks newTask = new Tasks(1, "Running", "Running 5km");
		TaskDTO newTaskDTO = new TaskDTO(1, "Running", "Running 5km");
		MockHttpServletRequestBuilder mockRequest = 
				MockMvcRequestBuilders.request(HttpMethod.PUT, "/task/1");
		
		mockRequest.contentType(MediaType.APPLICATION_JSON);
		mockRequest.content(objectMapper.writeValueAsString(newTask));
		mockRequest.accept(MediaType.APPLICATION_JSON);
		
		ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();
		ResultMatcher contentMatcher = MockMvcResultMatchers.content()
				.json(objectMapper.writeValueAsString(newTaskDTO));
		
		mvc.perform(mockRequest)
		   .andExpect(statusMatcher)
		   .andExpect(contentMatcher);
	}
	
	@Test
	void deleteTasks() throws Exception {
	MockHttpServletRequestBuilder mockRequest = 
			MockMvcRequestBuilders.request(HttpMethod.DELETE, "/task/1");
	ResultMatcher statusMatcher = MockMvcResultMatchers.status().isNoContent();
	ResultMatcher contentMatcher = MockMvcResultMatchers.content().string("true");
	mvc.perform(mockRequest)
	   .andExpect(statusMatcher)
	   .andExpect(contentMatcher);
	
	}
}
