package ru.sentyurin.TaskManagementSystem.repositories;

import org.springframework.stereotype.Repository;

import ru.sentyurin.TaskManagementSystem.models.Person;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
	Optional<Person> findById(int id);
	Optional<Person> findByEmail(String email);
}
