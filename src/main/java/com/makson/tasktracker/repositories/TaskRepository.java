package com.makson.tasktracker.repositories;

import com.makson.tasktracker.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}
