package com.makson.tasktracker.dto;

public record TaskUpdateDto(
        String title,
        String text,
        String status) {
}
