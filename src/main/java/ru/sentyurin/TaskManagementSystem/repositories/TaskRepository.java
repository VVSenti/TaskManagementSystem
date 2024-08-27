package ru.sentyurin.TaskManagementSystem.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sentyurin.TaskManagementSystem.models.Task;

public interface TaskRepository extends JpaRepository<Task, Integer>{
	//Optional<Task> findByAuthorId(int authorId);
}
