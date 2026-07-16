package br.com.kaiky.todo_list.tools;

import br.com.kaiky.todo_list.dto.TaskRequestDTO;
import br.com.kaiky.todo_list.dto.TaskResponseDTO;
import br.com.kaiky.todo_list.dto.TaskStatusDTO;
import br.com.kaiky.todo_list.service.TaskService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TaskTools {
    private final TaskService taskService;

    public TaskTools(TaskService taskService) {
        this.taskService = taskService;
    }

    @Tool(
            description = """
                Retrieves a task using its UUID.
                Use this tool when the user asks for information
                about a specific task and provides its ID.
            """
    )
    public TaskResponseDTO findTaskById(UUID id){
        return taskService.findTaskById(id);
    }

    @Tool(
            description = """
        Retrieves a paginated list of tasks.

        Use page 0 and size 20 when the user does not specify pagination.
        """
    )
    public List<TaskResponseDTO> getAllTasks(
            int page,
            int size
    ) {
        return taskService
                .getAllTasks(PageRequest.of(page, size))
                .getContent();
    }

    @Tool(
            description = """
                Creates a new task.
                Use this tool when the user explicitly asks to create a task.
                A title is required and a description is optional.
            """
    )
    public TaskResponseDTO createTask(TaskRequestDTO dto){
        return taskService.createTask(dto);
    }

    @Tool(
            description = """
                Updates the completion status of existing task.
                Use completed=true to mark the task as completed.
                And completed=false to mark the task is pending.
            """
    )
    public String updateTaskStatus(
            UUID id,
            boolean completed
    ){
        taskService.updateTaskStatus(
                id,
                new TaskStatusDTO(completed));
        return "Task with id: "+ id + " has been updated status completed.";
    }

    @Tool(
            description = """
                Permanently deletes a task using its UUID.
                Only use this tool when the user clearly and explicitly
                requests deletion.
                Do not execute it for ambiguous requests.
            """
    )
    public String deleteTask(UUID id){
        taskService.deleteTaskById(id);

        return "Task with id: "+ id + " has been deleted.";
    }

}
