package br.com.kaiky.todo_list.service;

import org.springframework.stereotype.Service;

import br.com.kaiky.todo_list.entity.Task;
import br.com.kaiky.todo_list.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        task.setCompleted(false);

        return taskRepository.save(task);
    }
}
