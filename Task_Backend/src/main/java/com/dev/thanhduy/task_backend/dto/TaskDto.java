package com.dev.thanhduy.task_backend.dto;

import com.dev.thanhduy.task_backend.enums.TaskStatus;
import lombok.Data;

import java.util.Date;

@Data
public class TaskDto {

    private long id;

    private String title;

    private String description;

    private Date dueDate;

    private String priority;

    private TaskStatus taskStatus;

    private long employeeId;

    private String employeeName;
}
