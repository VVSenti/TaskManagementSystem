package ru.sentyurin.TaskManagementSystem.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Task")
public class Task {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "title")
	@NotEmpty(message = "Title should not be empty!")
	private String title;

	@Column(name = "description")
	@NotEmpty(message = "Description should not be empty!")
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	@NotNull(message = "Status should not be empty!")
	private Status status;

	@Enumerated(EnumType.STRING)
	@Column(name = "priority")
	@NotNull(message = "Priority should not be empty!")
	private Priority priority;

	@ManyToOne
	@JoinColumn(name = "author_id", referencedColumnName = "id")
	@NotNull(message = "Author should not be empty!")
	private Person author;

	@ManyToOne
	@JoinColumn(name = "executor_id", referencedColumnName = "id")
	private Person executor;

	@OneToMany(mappedBy = "task")
	private List<Comment> comments = new ArrayList<>();

	public Task() {
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Status getStatus() {
		return status;
	}

	public Priority getPriority() {
		return priority;
	}

	public Person getAuthor() {
		return author;
	}

	public Person getExecutor() {
		return executor;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public void setAuthor(Person author) {
		this.author = author;
	}

	public void setExecutor(Person executor) {
		this.executor = executor;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", title=" + title + ", description=" + description + ", status="
				+ status + ", priority=" + priority + ", author=" + author + ", executor="
				+ executor + "]";
	}

}
