package ru.sentyurin.TaskManagementSystem.servicies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.sentyurin.TaskManagementSystem.models.Comment;
import ru.sentyurin.TaskManagementSystem.models.Task;
import ru.sentyurin.TaskManagementSystem.repositories.CommentRepository;
import ru.sentyurin.TaskManagementSystem.repositories.TaskRepository;
import ru.sentyurin.TaskManagementSystem.util.CommentNotFoundException;
import ru.sentyurin.TaskManagementSystem.util.TaskNotFoundException;

@Service
@Transactional(readOnly = true)
public class CommentService {

	private final CommentRepository commentRepository;
	private final TaskRepository taskRepository;

	@Autowired
	public CommentService(CommentRepository commentRepository, TaskRepository taskRepository) {
		this.commentRepository = commentRepository;
		this.taskRepository = taskRepository;
	}

	public Comment findOne(int id) {
		return commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
	}

	public List<Comment> findAllByTaskId(int id) {
		Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
		return commentRepository.findByTask(task);
	}
	
	@Transactional
	public void deleteById(int id) {
		commentRepository.deleteById(id);
	}

	@Transactional
	public void save(Comment comment) {
		commentRepository.save(comment);
	}
}
