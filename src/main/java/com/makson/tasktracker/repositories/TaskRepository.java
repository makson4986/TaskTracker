package com.makson.tasktracker.repositories;

import com.makson.tasktracker.models.Task;
import com.makson.tasktracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> getTasksByOwner(User user);
}
