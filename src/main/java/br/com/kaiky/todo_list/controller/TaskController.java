package br.com.kaiky.todo_list.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.kaiky.todo_list.entity.Task;
import br.com.kaiky.todo_list.service.TaskService;



@RestController
@RequestMapping("/api/task")
public class TaskController {
    
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/addTask")
    public Task addTask(Task task) {
        return taskService.createTask(task);
    }

}