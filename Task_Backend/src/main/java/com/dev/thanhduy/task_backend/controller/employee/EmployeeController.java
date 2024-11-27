package com.dev.thanhduy.task_backend.controller.employee;

import com.dev.thanhduy.task_backend.dto.CommentDto;
import com.dev.thanhduy.task_backend.dto.TaskDto;
import com.dev.thanhduy.task_backend.service.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> getTasksByUserId() {
        return ResponseEntity.ok(employeeService.getTasksByUserId());
    }

    @GetMapping("/tasks/{id}/{status}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable long id, @PathVariable String status) {
        TaskDto taskDto = employeeService.updateTask(id, status);
        if (taskDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(taskDto);
    }

    @GetMapping("/tasks/{task_id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long task_id) {
        return ResponseEntity.ok(employeeService.getTaskById(task_id));
    }

    @PostMapping("/tasks/comment/{task_id}")
    public ResponseEntity<CommentDto> createComment(@PathVariable long task_id, @RequestParam String content) {
        CommentDto commentDto = employeeService.createComment(task_id, content);
        if (commentDto == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDto);
    }

    @GetMapping("/comments/{task_id}")
    public ResponseEntity<List<CommentDto>> getCommentsByTaskId(@PathVariable Long task_id) {
        return ResponseEntity.ok(employeeService.getCommentsByTaskId(task_id));
    }
}
