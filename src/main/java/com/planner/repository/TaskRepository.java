package com.planner.repository;

import com.planner.entities.Task;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends BaseRepository<Task> {

//  @Query("SELECT t from Task t where t.taskCategory = ?1")
//  List<Task> findByTaskCategoryId(Long userId);

  @Query(value = "select * from tasks where id = :taskId and category_id = :categoryId", nativeQuery = true)
  Optional<Task> findByIdAndTaskCategoryId(@Param("taskId") Long taskId,@Param("categoryId") Long categoryId);

}
