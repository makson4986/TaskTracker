package com.makson.tasktracker.controllers;

import com.makson.tasktracker.dto.TaskRequestDto;
import com.makson.tasktracker.dto.TaskResponseDto;
import com.makson.tasktracker.models.User;
import com.makson.tasktracker.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TasksController {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<?> getAll(@AuthenticationPrincipal User user) {
        List<TaskResponseDto> tasks = taskService.getAll(user);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<?> addTask(@RequestBody TaskRequestDto task, @AuthenticationPrincipal User user) {
        TaskResponseDto createdTask = taskService.create(task, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }
}
