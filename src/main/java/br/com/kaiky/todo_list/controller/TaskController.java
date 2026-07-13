package br.com.kaiky.todo_list.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.kaiky.todo_list.dto.TaskRequestDTO;
import br.com.kaiky.todo_list.dto.TaskResponseDTO;
import br.com.kaiky.todo_list.service.TaskService;



@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(
        TaskRequestDTO dto) {

        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(taskService.createTask(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(
        @PathVariable UUID id
    ) {

        taskService.deleteTaskById(id);

        return ResponseEntity.noContent().build();

    }

}