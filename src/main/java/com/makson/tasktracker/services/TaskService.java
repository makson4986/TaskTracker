package com.makson.tasktracker.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makson.tasktracker.dto.TaskRequestDto;
import com.makson.tasktracker.dto.TaskResponseDto;
import com.makson.tasktracker.mappers.TaskMapper;
import com.makson.tasktracker.models.Task;
import com.makson.tasktracker.models.User;
import com.makson.tasktracker.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper mapper;

    @Transactional
    public TaskResponseDto create(TaskRequestDto taskDto, User user) {
        Task task = mapper.toEntity(taskDto);
        task.setOwner(user);
        Task savedTask = taskRepository.save(task);
        return mapper.toDto(savedTask);
    }
}
