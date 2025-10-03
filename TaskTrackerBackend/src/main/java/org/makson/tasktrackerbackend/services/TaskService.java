package org.makson.tasktrackerbackend.services;

import org.makson.tasktrackerbackend.dto.TaskCreationDto;
import org.makson.tasktrackerbackend.dto.TaskResponseDto;
import org.makson.tasktrackerbackend.dto.TaskUpdateDto;
import org.makson.tasktrackerbackend.exceptions.TaskNotFoundException;
import org.makson.tasktrackerbackend.mappers.TaskMapper;
import org.makson.tasktrackerbackend.models.Task;
import org.makson.tasktrackerbackend.models.TaskStatus;
import org.makson.tasktrackerbackend.models.User;
import org.makson.tasktrackerbackend.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper mapper;

    @Transactional(readOnly = true)
    public List<TaskResponseDto> getAll(User user) {
        List<Task> tasks = taskRepository.getTasksByOwner(user);
        return mapper.toDto(tasks);
    }

    @Transactional
    public TaskResponseDto create(TaskCreationDto taskDto, User user) {
        Task task = mapper.toEntity(taskDto);
        task.setOwner(user);
        Task savedTask = taskRepository.save(task);
        return mapper.toDto(savedTask);
    }

    @Transactional(readOnly = true)
    public TaskResponseDto findById(Integer id, User user) {
        Optional<Task> optionalTask = taskRepository.findByIdAndOwner(id, user);
        Task task = checkExists(optionalTask);
        return mapper.toDto(task);
    }

    @Transactional
    public void deleteById(Integer id, User user) {
        taskRepository.deleteByIdAndOwner(id, user);
    }

    @Transactional
    public TaskResponseDto update(TaskUpdateDto taskDto, User user, Integer id) {
        Optional<Task> optionalTask = taskRepository.findByIdAndOwner(id, user);
        Task task = checkExists(optionalTask);

        if (!taskDto.text().isBlank()) {
            task.setText(taskDto.text());
        }

        if (!taskDto.title().isBlank()) {
            task.setTitle(taskDto.title());
        }

        try {
            task.setStatus(TaskStatus.valueOf(taskDto.status()));
        }  catch (IllegalArgumentException | NullPointerException _) {

        }


        Task updatedTask = taskRepository.save(task);
        return mapper.toDto(updatedTask);
    }


    private Task checkExists(Optional<Task> task) {
        return task.orElseThrow(() -> new TaskNotFoundException("Task isn't found"));
    }
}
