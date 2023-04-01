package com.planner.permission;

import com.planner.entities.Task;
import com.planner.entities.TaskCategory;
import com.planner.entities.Users;
import com.planner.exception.task.TaskNotFoundException;
import com.planner.exception.taskcategory.TaskCategoryNotFoundException;
import com.planner.exception.user.IncorrectDataException;
import com.planner.repository.TaskCategoryRepository;
import com.planner.repository.TaskRepository;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskPermissionImpl {

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private TaskCategoryRepository taskCategoryRepository;

  public Task canViewTask(Users users, Long taskId, Long taskCategoryId) {
    TaskCategory taskCategory = taskCategoryRepository.findById(taskCategoryId)
        .orElseThrow(
            TaskCategoryNotFoundException::new);
    Task task = taskRepository.findByIdAndTaskCategoryId(taskId, taskCategoryId).orElseThrow(
        TaskNotFoundException::new);
    if (taskCategory.getUsers() != users.getId()) {
      throw new IncorrectDataException();
    }
    return task;
  }

  public void isCategoryOwner(Users users, Long taskCategoryId) {
    TaskCategory taskCategory = taskCategoryRepository.findById(taskCategoryId)
        .orElseThrow(
            TaskCategoryNotFoundException::new);
    if (!Objects.equals(taskCategory.getUsers(), users.getId())) {
      throw new IncorrectDataException();
    }
  }

}
