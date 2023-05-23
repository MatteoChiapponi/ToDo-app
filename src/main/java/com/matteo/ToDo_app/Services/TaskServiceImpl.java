package com.matteo.ToDo_app.Services;

import com.matteo.ToDo_app.Dtos.TaskDto.PostTaskDto;
import com.matteo.ToDo_app.Entitys.Task;
import com.matteo.ToDo_app.Entitys.User.User;
import com.matteo.ToDo_app.Exceptions.UserDoesNotOwnTask;
import com.matteo.ToDo_app.Exceptions.EntityNotFoudException;
import com.matteo.ToDo_app.Repositorys.TaskRepository;
import com.matteo.ToDo_app.Repositorys.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements ITaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Task saveTask(PostTaskDto postTaskDto, Long userId) throws EntityNotFoudException {
        if (userRepository.existsById(userId)) {
            Task taskToSave = new Task(postTaskDto.description(),false,LocalDateTime.now());
            taskToSave.setUser(new User());
            taskToSave.getUser().setId(userId);
            return taskRepository.save(taskToSave);
        }
        else
            throw new EntityNotFoudException("user do not exist");
    }


    @Override
    public List<Task> findAllUserTaskByStatus(Long userId, Boolean completed) throws EntityNotFoudException {
        if (userRepository.existsById(userId))
            return taskRepository.findTaskByUserAndCompleted(userId, completed);
        throw new EntityNotFoudException("user not found to show tasks");
    }

    @Override
    public List<Task> findAllUserTasks(Long id) throws EntityNotFoudException {
        if (userRepository.existsById(id))
            return taskRepository.findTasksByUser(id);
        throw new EntityNotFoudException("user not found to show tasks");
    }


    @Override
    public void updateStatusTask(Long taskId, Boolean status,Long userId) throws UserDoesNotOwnTask, EntityNotFoudException {
        if (!taskRepository.existsById(taskId))
            throw new EntityNotFoudException("task not found to update status");
        else {
            if (validateUserOwnsTask(userId,taskId)) {
                if (status.equals(true))
                    taskRepository.updateTaskFinishedAt(taskId, LocalDateTime.now());
                else
                    taskRepository.updateTaskFinishedAt(taskId, null);
                taskRepository.updateTaskStatus(taskId, status);
            }else
                throw new UserDoesNotOwnTask("can't update task status if you didn't create");
        }
    }

    @Override
    public void updateDescriptionTask(Long taskId, String description,Long userId) throws UserDoesNotOwnTask, EntityNotFoudException {
        if (!taskRepository.existsById(taskId))
            throw new EntityNotFoudException("task not found to update status");
        else {
            if (validateUserOwnsTask(userId,taskId))
                taskRepository.updateTaskDescription(taskId,description);
            else
                throw new UserDoesNotOwnTask("can't update task description if you didn't create");
        }
    }

    @Override
    public List<Task> findAllTask() {
        return taskRepository.findAll();
    }

    @Override
    public void deleteTask(Long taskId,Long userId) throws UserDoesNotOwnTask, EntityNotFoudException {
        if (!taskRepository.existsById(taskId))
            throw new EntityNotFoudException("task not found to delete");
        if (validateUserOwnsTask(userId,taskId))
            taskRepository.deleteById(taskId);
        else
            throw new UserDoesNotOwnTask("can't delete a task you didn't create");
    }
    private boolean validateUserOwnsTask(Long userId,Long taskId){
        var task = taskRepository.findUserTaskByIds(userId,taskId);
        if (task.isPresent())
            return true;
        return false;
    }
}
