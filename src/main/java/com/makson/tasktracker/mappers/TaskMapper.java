package com.makson.tasktracker.mappers;

import com.makson.tasktracker.dto.TaskCreationDto;
import com.makson.tasktracker.dto.TaskResponseDto;
import com.makson.tasktracker.models.Task;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TaskMapper {
    TaskResponseDto toDto(Task task);

    List<TaskResponseDto> toDto(List<Task> tasks);

    Task toEntity(TaskCreationDto taskDto);
}
