package com.matteo.ToDo_app.Repositorys;

import com.matteo.ToDo_app.Entitys.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    @Modifying
    @Query("UPDATE Task t SET t.completed = :newStatus WHERE t.id = :taskId")
    @Transactional
    void updateTaskStatus(@Param("taskId") Long taskId, @Param("newStatus") Boolean newStatus);

    @Modifying
    @Query("UPDATE Task t SET t.finishedAt = :dateTime WHERE t.id = :taskId")
    @Transactional
    void updateTaskFinishedAt(@Param("taskId") Long taskId, @Param("dateTime") LocalDateTime dateTime);

    @Modifying
    @Query("UPDATE Task t SET t.description = :description WHERE t.id = :taskId")
    @Transactional
    void updateTaskDescription(@Param("taskId") Long taskId, @Param("description") String description);

    @Query(value = "SELECT t FROM Task t WHERE t.user.id = :userId")
    List<Task> findTasksByUser(@Param("userId") Long userId);

    @Query("SELECT t FROM Task t WHERE t.user.id = :userId AND t.completed = :completed")
    List<Task> findTaskByUserAndCompleted(@Param("userId") Long userId, @Param("completed") Boolean completed);

    @Query("SELECT t FROM Task t WHERE t.user.id = :userId AND t.id= :taskId")
    Optional<Task> findUserTaskByIds(@Param("userId") Long userId, @Param("taskId") Long taskId);
}
