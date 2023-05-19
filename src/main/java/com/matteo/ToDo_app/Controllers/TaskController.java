package com.matteo.ToDo_app.Controllers;

import com.matteo.ToDo_app.Dtos.TaskDto.PostTaskDto;
import com.matteo.ToDo_app.Dtos.TaskDto.UpdateTaskStatusDto;
import com.matteo.ToDo_app.Entitys.Task;
import com.matteo.ToDo_app.Services.TaskServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {
    private TaskServiceImpl taskService;

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getUserTasks() throws Exception {
        Long userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getCredentials());
        return ResponseEntity.ok(taskService.findAllUserTasks(userId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserTask(@PathVariable @Positive(message = "must be a positive number")Long id){
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{completed}")
    public ResponseEntity<List<Task>> getAllTasksByStatus(@PathVariable @Pattern(regexp = "^(true|false)$", message = "must be a boolean value") String completed) throws Exception {
        var userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getCredentials());
        return ResponseEntity.ok(taskService.findAllUserTaskByStatus(userId, Boolean.parseBoolean(completed)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks() throws Exception {
        return ResponseEntity.ok(taskService.findAllTask());
    }

    @PostMapping
    public ResponseEntity<String> saveTask(@Valid @RequestBody PostTaskDto postTaskDto) throws Exception {
        var userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getCredentials());
        taskService.saveTask(postTaskDto,userId);
        return ResponseEntity.ok("task saved successfully");
    }

    @PatchMapping("/completed/{id}")
    public ResponseEntity<String> changeStatusTask(@PathVariable @Positive(message = "must be a positive number") Long id, @RequestBody @Valid UpdateTaskStatusDto completed) throws Exception {
        taskService.updateStatusTask(id, Boolean.parseBoolean(completed.completed()));
        return ResponseEntity.ok("task status changed successfully");
    }

    @PatchMapping("/description/{id}")
    public ResponseEntity<String> changeDescriptionTask(@PathVariable @Positive(message = "must be a positive number") Long id, @RequestBody @Valid PostTaskDto postTaskDto) throws Exception {
        taskService.updateDescriptionTask(id, postTaskDto.description());
        return ResponseEntity.ok("task description changed successfully");
    }
}
