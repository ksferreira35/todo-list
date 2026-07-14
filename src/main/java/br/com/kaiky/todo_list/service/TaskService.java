package br.com.kaiky.todo_list.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.kaiky.todo_list.dto.TaskRequestDTO;
import br.com.kaiky.todo_list.dto.TaskResponseDTO;
import br.com.kaiky.todo_list.dto.TaskStatusDTO;
import br.com.kaiky.todo_list.entity.Task;
import br.com.kaiky.todo_list.exception.ResourceNotFoundException;
import br.com.kaiky.todo_list.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponseDTO createTask(TaskRequestDTO dto) {
        
        Task task = new Task(
            dto.title(),
            dto.description()
        );

        Task saveTask = taskRepository.save(task);

        return convertToResponseDTO(saveTask);
    }

    public Page<TaskResponseDTO> getAllTasks(Pageable pageable) {
        Page<Task> tasks = taskRepository.findAll(pageable);

        return tasks.map(this::convertToResponseDTO);
    }

    public TaskResponseDTO findTaskById(UUID id) {
        Task task = taskRepository.findById(id)
                                  .orElseThrow(() -> new ResourceNotFoundException("Task with id: "+ id + "Not Found"));
        
        return convertToResponseDTO(task);
    }

    public void updateTaskStatus(UUID id, TaskStatusDTO dto) {
        Task task = taskRepository.findById(id)
                                  .orElseThrow(() -> new ResourceNotFoundException("Task with id: "+ id + "Not Found"));
        
        task.setCompleted(dto.completed());
        
        taskRepository.save(task);
    }
    
    public void deleteTaskById(UUID id) {
        Task task = taskRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Task não encontrada"));

        taskRepository.delete(task);                        
    }

    public TaskResponseDTO convertToResponseDTO(Task task) {
        return new TaskResponseDTO(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.isCompleted()
        );

    }
}
