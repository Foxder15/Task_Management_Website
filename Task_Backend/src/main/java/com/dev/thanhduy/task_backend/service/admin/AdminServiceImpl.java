package com.dev.thanhduy.task_backend.service.admin;

import com.dev.thanhduy.task_backend.dto.CommentDto;
import com.dev.thanhduy.task_backend.dto.TaskDto;
import com.dev.thanhduy.task_backend.dto.UserDto;
import com.dev.thanhduy.task_backend.entity.Comment;
import com.dev.thanhduy.task_backend.entity.Task;
import com.dev.thanhduy.task_backend.entity.User;
import com.dev.thanhduy.task_backend.enums.TaskStatus;
import com.dev.thanhduy.task_backend.enums.UserRole;
import com.dev.thanhduy.task_backend.exception.CommentFailedException;
import com.dev.thanhduy.task_backend.exception.UserExistedException;
import com.dev.thanhduy.task_backend.repository.CommentRepository;
import com.dev.thanhduy.task_backend.repository.TaskRepository;
import com.dev.thanhduy.task_backend.repository.UserRepository;
import com.dev.thanhduy.task_backend.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    private final JwtUtil jwtUtil;

    private final CommentRepository commentRepository;

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getUserRole() == UserRole.EMPLOYEE || user.getUserRole() == UserRole.TESTER || user.getUserRole() == UserRole.DEVELOPER)
                .map(User::getUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        Optional<User> optionalUser = userRepository.findById(taskDto.getEmployeeId());
        if (optionalUser.isPresent()) {
            Task task = new Task();
            task.setTitle(taskDto.getTitle());
            task.setDescription(taskDto.getDescription());
            task.setPriority(taskDto.getPriority());
            task.setDueDate(taskDto.getDueDate());
            task.setTaskStatus(TaskStatus.PENDING);
            task.setUser(optionalUser.get());
            return taskRepository.save(task).getTaskDto();
        }
        return null;
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<TaskDto> getTasksByUserId(long id) {
        return taskRepository.findAllTasksByUserId(id)
                .stream()
                .sorted(Comparator.comparing(Task::getDueDate).reversed())
                .map(Task::getTaskDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getTasks() {
        return taskRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Task::getDueDate).reversed())
                .map(Task::getTaskDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public TaskDto getTaskById(long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.map(Task::getTaskDto).orElse(null);
    }

    @Override
    public UserDto getUserById(Long taskId) {
        Optional<User> optionalUser = userRepository.findById(taskId);
        return optionalUser.map(User::getUserDto).orElse(null);
    }

    @Override
    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        Optional<User> optionalUser = userRepository.findById(taskDto.getEmployeeId());
        if (optionalTask.isPresent() && optionalUser.isPresent()) {
            Task task = optionalTask.get();
            task.setTitle(taskDto.getTitle());
            task.setDescription(taskDto.getDescription());
            task.setPriority(taskDto.getPriority());
            task.setDueDate(taskDto.getDueDate());
            task.setTaskStatus(mapStringToTaskStatus(String.valueOf(taskDto.getTaskStatus())));
            task.setUser(optionalUser.get());
            return taskRepository.save(task).getTaskDto();
        }
        return null;
    }

    @Override
    public UserDto updateUser(long id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        Optional<User> checkExistMailUser = userRepository.findFirstByEmail(userDto.getEmail());
        if (optionalUser.isPresent() && (checkExistMailUser.isEmpty() || checkExistMailUser.get().getEmail().equals(userDto.getEmail()))) {
            User user = optionalUser.get();
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setUserRole(userDto.getUserRole());
            return userRepository.save(user).getUserDto();
        }

        return null;
    }

    @Override
    public List<TaskDto> searchTaskByTitle(String title) {
        return taskRepository.findAllByTitleContaining(title)
                .stream()
                .sorted(Comparator.comparing(Task::getDueDate).reversed())
                .map(Task::getTaskDto)
                .collect(Collectors.toList());
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
    public List<CommentDto> getCommentsByTaskId(long taskId) {
        return commentRepository.findAllByTaskId(taskId)
                .stream()
                .map(Comment::getCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDto.getEmail());
        if (optionalUser.isEmpty()) {
            User user = new User();
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
            user.setUserRole(mapStringToUserRole(String.valueOf(userDto.getUserRole())));
            return userRepository.save(user).getUserDto();
        }

        throw new UserExistedException("User has already existed.");
    }

    private UserRole mapStringToUserRole(String role) {
        return switch (role) {
            case "TESTER" -> UserRole.TESTER;
            case "DEVELOPER" -> UserRole.DEVELOPER;
            default -> UserRole.EMPLOYEE;

        };
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
