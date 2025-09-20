package com.makson.tasktracker.dto;

import jakarta.validation.constraints.NotBlank;

public record TaskRequestDto(
        @NotBlank(message = "Title must not be empty")
        String title,
        @NotBlank(message = "Text must not be empty")
        String text) {
}
