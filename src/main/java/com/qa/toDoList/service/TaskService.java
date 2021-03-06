package com.qa.toDoList.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.toDoList.data.models.Tasks;
import com.qa.toDoList.data.respository.TaskRepository;
import com.qa.toDoList.dto.TaskDTO;
import com.qa.toDoList.mappers.TaskMapper;

@Service
public class TaskService {
	
	private TaskRepository taskRepository;
	
	private TaskMapper taskMapper;
	
	@Autowired
	public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
		this.taskRepository = taskRepository;
		this.taskMapper = taskMapper;
	}
	
	public List<TaskDTO> readByID (Integer ID) {
		List<Tasks> tasker = taskRepository.findForToDoList(ID);
		List<TaskDTO> taskDTOs = new ArrayList<TaskDTO>();
		
		tasker.forEach(task -> taskDTOs.add(taskMapper.mapToDTO(task)));
		return taskDTOs;
	}
	
	public TaskDTO createTask (Tasks task) {
		Tasks newTask = taskRepository.save(task);
		
		return taskMapper.mapToDTO(newTask);
	}
	
	public TaskDTO updateTask (Integer id, Tasks task) throws EntityNotFoundException {
		Optional<Tasks> taskinDBOpt = taskRepository.findById(id);
		Tasks taskInDB;
		
		if (taskinDBOpt.isPresent()) {
			taskInDB = taskinDBOpt.get();
		} else {
			throw new EntityNotFoundException();
		}
		
		taskInDB.setName(task.getName());
		taskInDB.setDescription(task.getDescription());
		
		Tasks updatedTask = taskRepository.save(taskInDB);
		return taskMapper.mapToDTO(updatedTask);
	}
	
	
	public boolean deleteTask(Integer id) {
		if (!taskRepository.existsById(id)) {
			throw new EntityNotFoundException();
		}
		
		taskRepository.deleteById(id);
		boolean youThere = taskRepository.existsById(id);
		
		return !youThere;
		
	}

}
