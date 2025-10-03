package org.makson.tasktrackerbackend.controllers;

import org.makson.tasktrackerbackend.dto.TaskCreationDto;
import org.makson.tasktrackerbackend.dto.TaskResponseDto;
import org.makson.tasktrackerbackend.dto.TaskUpdateDto;
import org.makson.tasktrackerbackend.models.User;
import org.makson.tasktrackerbackend.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<?> addTask(@Validated @RequestBody TaskCreationDto task, @AuthenticationPrincipal User user) {
        TaskResponseDto createdTask = taskService.create(task, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        TaskResponseDto task = taskService.findById(id, user);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        taskService.deleteById(id, user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody TaskUpdateDto task, @AuthenticationPrincipal User user, @PathVariable Integer id) {
        TaskResponseDto updatedTask = taskService.update(task, user, id);
        return ResponseEntity.ok(updatedTask);
    }
}
