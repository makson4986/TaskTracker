package com.makson.tasktracker.dto;

import com.makson.tasktracker.models.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponseDto(
        int id,
        String title,
        String text,
        UserDto owner,
        TaskStatus status,
        LocalDateTime performedAt
) {
}
