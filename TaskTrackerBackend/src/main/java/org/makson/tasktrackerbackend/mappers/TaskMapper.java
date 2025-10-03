package org.makson.tasktrackerbackend.mappers;

import org.makson.tasktrackerbackend.dto.TaskCreationDto;
import org.makson.tasktrackerbackend.dto.TaskResponseDto;
import org.makson.tasktrackerbackend.models.Task;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TaskMapper {
    TaskResponseDto toDto(Task task);

    List<TaskResponseDto> toDto(List<Task> tasks);

    Task toEntity(TaskCreationDto taskDto);
}
