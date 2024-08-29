package ru.sentyurin.TaskManagementSystem.util;

import java.lang.reflect.Field;

import ru.sentyurin.TaskManagementSystem.models.Person;
import ru.sentyurin.TaskManagementSystem.models.Task;

public class Experiment {
	public static void main(String[] args) throws Exception {
		Task oldTask = new Task();
		Task newTask = new Task();
		
		newTask.setTitle("asdasd");
		newTask.setAuthor(new Person());
		
		oldTask.setId(5);
		
		System.out.println(oldTask);
		
		Field[] fields = Task.class.getDeclaredFields();
		for (Field field : fields) {
			if ("id".equals(field.getName())) continue;
			field.setAccessible(true);
			try {
				Object value = field.get(newTask);
				if(value != null) field.set(oldTask, value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			field.setAccessible(false);
		}
		
		System.out.println(oldTask);
	}
}
