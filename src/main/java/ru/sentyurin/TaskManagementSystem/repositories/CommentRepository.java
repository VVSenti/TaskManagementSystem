package ru.sentyurin.TaskManagementSystem.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.sentyurin.TaskManagementSystem.models.Comment;
import ru.sentyurin.TaskManagementSystem.models.Task;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{
	List<Comment> findByTask(Task Task);
}
