package com.planner.repository;

import com.planner.entities.TaskCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskCategoryRepository extends BaseRepository<TaskCategory> {

  List<TaskCategory> findAllByUsers(Long userId);

  Optional<TaskCategory> findByUsersAndId(Long userId, Long id);
}
