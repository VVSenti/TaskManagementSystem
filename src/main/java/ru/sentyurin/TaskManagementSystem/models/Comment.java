package ru.sentyurin.TaskManagementSystem.models;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Comment")
public class Comment {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "task_id", referencedColumnName = "id")
	private Task task;

	@ManyToOne
	@JoinColumn(name = "author_id", referencedColumnName = "id")
	private Person author;

	@Column(name = "text")
	private String text;

	public Comment() {
	}

	public int getId() {
		return id;
	}

	public Task getTask() {
		return task;
	}

	public Person getAuthor() {
		return author;
	}

	public String getText() {
		return text;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public void setAuthor(Person author) {
		this.author = author;
	}

	public void setText(String text) {
		this.text = text;
	}

}
