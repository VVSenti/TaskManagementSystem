package ru.sentyurin.TaskManagementSystem.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sentyurin.TaskManagementSystem.models.Person;
import ru.sentyurin.TaskManagementSystem.models.Task;

public interface TaskRepository extends JpaRepository<Task, Integer>{
	List<Task> findByAuthor(Person author);
	List<Task> findByExecutor(Person executor);
}
