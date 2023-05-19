package com.matteo.ToDo_app.Services;

import com.matteo.ToDo_app.Dtos.TaskDto.PostTaskDto;
import com.matteo.ToDo_app.Entitys.Task;

import java.util.List;

public interface ITaskService {
    Task saveTask(PostTaskDto postTaskDto, Long userId) throws Exception;
    List<Task> findAllUserTaskByStatus(Long userId, Boolean completed) throws Exception;
    List<Task> findAllUserTasks(Long id) throws Exception;
    void updateStatusTask(Long id, Boolean status) throws Exception;
    void updateDescriptionTask(Long id, String description) throws Exception;

    List<Task> findAllTask();
    void deleteTask(Long id);
}
