package ru.sentyurin.TaskManagementSystem.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
	private List<Task> createdTasks = new ArrayList<>();

	@OneToMany(mappedBy = "executor", fetch = FetchType.LAZY)
	private List<Task> tasksToExecute = new ArrayList<>();

	public Person() {
	}

	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public List<Task> getCreatedTasks() {
		return createdTasks;
	}

	public List<Task> getTasksToExecute() {
		return tasksToExecute;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCreatedTasks(List<Task> createdTasks) {
		this.createdTasks = createdTasks;
	}

	public void setTasksToExecute(List<Task> tasksToExecute) {
		this.tasksToExecute = tasksToExecute;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", email=" + email + ", password=" + password + "]";
	}

}
