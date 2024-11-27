package com.dev.thanhduy.task_backend.service.employee;

import com.dev.thanhduy.task_backend.dto.CommentDto;
import com.dev.thanhduy.task_backend.dto.TaskDto;
import com.dev.thanhduy.task_backend.entity.Comment;
import com.dev.thanhduy.task_backend.entity.Task;
import com.dev.thanhduy.task_backend.entity.User;
import com.dev.thanhduy.task_backend.enums.TaskStatus;
import com.dev.thanhduy.task_backend.exception.CommentFailedException;
import com.dev.thanhduy.task_backend.exception.TaskNotFoundException;
import com.dev.thanhduy.task_backend.exception.UserNotFoundException;
import com.dev.thanhduy.task_backend.repository.CommentRepository;
import com.dev.thanhduy.task_backend.repository.TaskRepository;
import com.dev.thanhduy.task_backend.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final TaskRepository taskRepository;

    private final CommentRepository commentRepository;

    private final JwtUtil jwtUtil;
    @Override
    public List<TaskDto> getTasksByUserId() {
        User user = jwtUtil.getLoggedInUser();
        System.out.println(user);
        if (user != null) {
            return taskRepository.findAllTasksByUserId(user.getId())
                    .stream()
                    .sorted(Comparator.comparing(Task::getDueDate).reversed())
                    .map(Task::getTaskDto)
                    .collect(Collectors.toList());
        }
        throw new UserNotFoundException("User not found");
    }

    @Override
    public TaskDto updateTask(long id, String status) {
        Optional<Task> task = taskRepository.findById(id);

        if (task.isPresent()) {
            Task taskToUpdate = task.get();
            taskToUpdate.setTaskStatus(mapStringToTaskStatus(status));
            return taskRepository.save(taskToUpdate).getTaskDto();
        }
        throw new TaskNotFoundException("Task not found");
    }

    @Override
    public TaskDto getTaskById(Long taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        return optionalTask.map(Task::getTaskDto).orElse(null);
    }

    @Override
    public CommentDto createComment(long taskId, String content) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        User user = jwtUtil.getLoggedInUser();

        if (optionalTask.isPresent() && user != null) {
            Comment comment = new Comment();
            comment.setCreateDate(new Date());
            comment.setContent(content);
            comment.setTask(optionalTask.get());
            comment.setAuthor(user);
            return commentRepository.save(comment).getCommentDto();
        }
        throw new CommentFailedException("User or Task not found");
    }

    @Override
    public List<CommentDto> getCommentsByTaskId(Long taskId) {
        return commentRepository.findAllByTaskId(taskId)
                .stream()
                .map(Comment::getCommentDto)
                .collect(Collectors.toList());
    }


    private TaskStatus mapStringToTaskStatus(String status) {
        return switch (status) {
            case "PENDING" -> TaskStatus.PENDING;
            case "IN_PROGRESS" -> TaskStatus.IN_PROGRESS;
            case "COMPLETED" -> TaskStatus.COMPLETED;
            case "DEFERRED" -> TaskStatus.DEFERRED;
            default -> TaskStatus.CANCELLED;
        };
    }
}
