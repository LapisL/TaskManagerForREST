package com.task.backend.task.domain;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.task.backend.common.entity.task.TaskEntity;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {

    @Query(value = "SELECT taskEntity FROM TaskEntity taskEntity WHERE taskEntity.taskId = :id")
    public TaskEntity findByTaskId(@Param("id") int id);
}


