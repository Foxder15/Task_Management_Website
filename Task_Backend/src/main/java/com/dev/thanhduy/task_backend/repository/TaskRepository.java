package com.dev.thanhduy.task_backend.repository;

import com.dev.thanhduy.task_backend.dto.TaskDto;
import com.dev.thanhduy.task_backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByTitleContaining(String title);

    List<Task> findAllTasksByUserId(long id);

    void deleteALlByUserId(long id);
}
