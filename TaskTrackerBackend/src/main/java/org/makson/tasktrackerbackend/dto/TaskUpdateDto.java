package org.makson.tasktrackerbackend.dto;

public record TaskUpdateDto(
        String title,
        String text,
        String status) {
}
