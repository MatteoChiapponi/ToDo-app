package com.matteo.ToDo_app.Services;

import com.matteo.ToDo_app.Dtos.TaskDto.PostTaskDto;
import com.matteo.ToDo_app.Entitys.Task;
import com.matteo.ToDo_app.Exceptions.BadRequestException;
import com.matteo.ToDo_app.Exceptions.UserDoesNotOwnTask;
import com.matteo.ToDo_app.Exceptions.EntityNotFoudException;

import java.util.List;

public interface ITaskService {
    Task saveTask(PostTaskDto postTaskDto, Long userId) throws BadRequestException, EntityNotFoudException;
    List<Task> findAllUserTaskByStatus(Long userId, Boolean completed) throws Exception;
    List<Task> findAllUserTasks(Long id) throws Exception;
    void updateStatusTask(Long taskId, Boolean status,Long userId) throws UserDoesNotOwnTask, EntityNotFoudException;
    void updateDescriptionTask(Long taskId, String description,Long userId) throws UserDoesNotOwnTask,EntityNotFoudException;

    List<Task> findAllTask();
    void deleteTask(Long taskId,Long userId) throws UserDoesNotOwnTask, EntityNotFoudException;
}
