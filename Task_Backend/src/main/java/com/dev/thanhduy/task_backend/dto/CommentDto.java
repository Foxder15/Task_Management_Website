package com.dev.thanhduy.task_backend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {

    private long id;

    private String content;

    private Date createDate;

    private long taskId;

    private long userId;

    private String postedBy;
}
