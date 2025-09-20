package com.makson.tasktracker.mappers;

import com.makson.tasktracker.dto.UserDto;
import com.makson.tasktracker.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
        UserDto toDto(User user);
}
