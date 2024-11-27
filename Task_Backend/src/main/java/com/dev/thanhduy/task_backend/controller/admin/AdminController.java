package com.dev.thanhduy.task_backend.controller.admin;

import com.dev.thanhduy.task_backend.dto.CommentDto;
import com.dev.thanhduy.task_backend.dto.TaskDto;
import com.dev.thanhduy.task_backend.dto.UserDto;
import com.dev.thanhduy.task_backend.entity.Task;
import com.dev.thanhduy.task_backend.entity.User;
import com.dev.thanhduy.task_backend.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(adminService.getUsers());
    }

    @GetMapping("/users/{task_id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long task_id) {
        return ResponseEntity.ok(adminService.getUserById(task_id));
    }


    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        System.out.println(userDto.getUserRole());
        UserDto user = adminService.createUser(userDto);
        if (user == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @DeleteMapping("/users/{user_id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long user_id) {
        adminService.deleteUser(user_id);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/users/{task_id}")
    public ResponseEntity<?> updateUser(@PathVariable Long task_id, @RequestBody UserDto userDto) {
        UserDto updateUser = adminService.updateUser(task_id, userDto);
        if (updateUser == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updateUser);
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        TaskDto createTaskDto = adminService.createTask(taskDto);
        if (createTaskDto == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(createTaskDto);
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getTasks() {
        return ResponseEntity.ok(adminService.getTasks());
    }

    @DeleteMapping("/tasks/{task_id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long task_id) {
        adminService.deleteTask(task_id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/tasks/{task_id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long task_id) {
        return ResponseEntity.ok(adminService.getTaskById(task_id));
    }

    @PutMapping("/tasks/{task_id}")
    public ResponseEntity<?> updateTask(@PathVariable Long task_id, @RequestBody TaskDto taskDto) {
        TaskDto updateTaskDto = adminService.updateTask(task_id, taskDto);
        if (updateTaskDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updateTaskDto);
    }

    @GetMapping("/users/tasks/{user_id}")
    public ResponseEntity<List<TaskDto>> getTasksByUserId(@PathVariable long user_id) {
        return ResponseEntity.ok(adminService.getTasksByUserId(user_id));
    }

    @GetMapping("/tasks/search/{keyword}")
    public ResponseEntity<List<TaskDto>> searchTasks(@PathVariable String keyword) {
        return ResponseEntity.ok(adminService.searchTaskByTitle(keyword));
    }

    @PostMapping("/tasks/comment/{task_id}")
    public ResponseEntity<CommentDto> createComment(@PathVariable long task_id, @RequestParam String content) {
        CommentDto commentDto = adminService.createComment(task_id, content);
        if (commentDto == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDto);
    }

    @GetMapping("/comments/{task_id}")
    public ResponseEntity<List<CommentDto>> getCommentsByTaskId(@PathVariable Long task_id) {
        return ResponseEntity.ok(adminService.getCommentsByTaskId(task_id));
    }

 }
