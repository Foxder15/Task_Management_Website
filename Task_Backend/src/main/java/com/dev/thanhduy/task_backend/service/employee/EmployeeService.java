package com.dev.thanhduy.task_backend.service.employee;

import com.dev.thanhduy.task_backend.dto.CommentDto;
import com.dev.thanhduy.task_backend.dto.TaskDto;

import java.util.List;

public interface EmployeeService {

    List<TaskDto> getTasksByUserId();

    TaskDto updateTask(long id, String status);

    TaskDto getTaskById(Long taskId);

    CommentDto createComment(long taskId, String content);

    List<CommentDto> getCommentsByTaskId(Long taskId);
}
