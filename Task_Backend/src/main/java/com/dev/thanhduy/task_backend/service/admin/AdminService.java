package com.dev.thanhduy.task_backend.service.admin;

import com.dev.thanhduy.task_backend.dto.CommentDto;
import com.dev.thanhduy.task_backend.dto.TaskDto;
import com.dev.thanhduy.task_backend.dto.UserDto;

import java.util.List;

public interface AdminService {

    List<UserDto> getUsers();

    TaskDto createTask(TaskDto taskDto);

    UserDto createUser(UserDto userDto);

    List<TaskDto> getTasks();

    void deleteTask(long id);

    void deleteUser(long id);

    TaskDto getTaskById(long id);

    UserDto updateUser(long id, UserDto userDto);

    TaskDto updateTask(Long id, TaskDto taskDto);

    List<TaskDto> getTasksByUserId(long id);

    List<TaskDto> searchTaskByTitle(String title);

    CommentDto createComment(long taskId, String content);

    List<CommentDto> getCommentsByTaskId(long taskId);

    UserDto getUserById(Long taskId);
}
