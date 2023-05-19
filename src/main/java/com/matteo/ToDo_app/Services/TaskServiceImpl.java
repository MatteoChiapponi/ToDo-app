package com.matteo.ToDo_app.Services;

import com.matteo.ToDo_app.Dtos.TaskDto.PostTaskDto;
import com.matteo.ToDo_app.Entitys.Task;
import com.matteo.ToDo_app.Entitys.User.User;
import com.matteo.ToDo_app.Repositorys.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements ITaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task saveTask(PostTaskDto postTaskDto, Long userId) throws Exception {
        Task taskToSave = new Task(postTaskDto.description(),false,LocalDateTime.now());
        taskToSave.setUser(new User());
        taskToSave.getUser().setId(userId);
        return taskRepository.save(taskToSave);
    }


    @Override
    public List<Task> findAllUserTaskByStatus(Long userId, Boolean completed) throws Exception {
        return taskRepository.findTaskByUserAndCompleted(userId, completed);
    }

    @Override
    public List<Task> findAllUserTasks(Long id) throws Exception {
        return taskRepository.findTasksByUser(id);
    }


    @Override
    public void updateStatusTask(Long id, Boolean status) throws Exception {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isEmpty()){
            throw new Exception("task not found to update status");
        }
        if (status.equals(true)){
            taskRepository.updateTaskFinishedAt(id,LocalDateTime.now());
        }
        taskRepository.updateTaskStatus(id,status);
    }

    @Override
    public void updateDescriptionTask(Long id, String description) throws Exception {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isEmpty()){
            throw new Exception("task not found to update status");
        }
        taskRepository.updateTaskDescription(id,description);
    }

    @Override
    public List<Task> findAllTask() {
        return taskRepository.findAll();
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
