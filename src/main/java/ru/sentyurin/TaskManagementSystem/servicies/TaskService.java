package ru.sentyurin.TaskManagementSystem.servicies;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.sentyurin.TaskManagementSystem.models.Task;
import ru.sentyurin.TaskManagementSystem.repositories.TaskRepository;
import ru.sentyurin.TaskManagementSystem.util.TaskNotFoundException;

@Service
@Transactional(readOnly = true)
public class TaskService {
	TaskRepository taskRepository;

	@Autowired
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
	
	public List<Task> findAll() {
		return taskRepository.findAll();
	}
	
	public Task findOne(int id) {
		return taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
	}
	
	@Transactional
	public void save(Task task) {
		taskRepository.save(task);
	}
}
