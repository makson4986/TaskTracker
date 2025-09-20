package com.makson.tasktracker.mappers;

import com.makson.tasktracker.dto.TaskRequestDto;
import com.makson.tasktracker.dto.TaskResponseDto;
import com.makson.tasktracker.models.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TaskMapper {
    TaskResponseDto toDto(Task task);
    Task toEntity(TaskRequestDto taskDto);
}
