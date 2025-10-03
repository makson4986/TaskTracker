package org.makson.tasktrackerbackend.repositories;

import org.makson.tasktrackerbackend.models.Task;
import org.makson.tasktrackerbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> getTasksByOwner(User user);

    Optional<Task> findByIdAndOwner(Integer id, User user);

    void deleteByIdAndOwner(Integer id, User user);
}

