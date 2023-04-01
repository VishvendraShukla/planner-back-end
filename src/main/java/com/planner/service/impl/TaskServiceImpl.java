package com.planner.service.impl;

import com.planner.entities.Task;
import com.planner.entities.TaskCategory;
import com.planner.entities.Users;
import com.planner.exception.taskcategory.TaskCategoryNotFoundException;
import com.planner.factory.TaskMapStructFactory;
import com.planner.model.CreateTaskVo;
import com.planner.model.TaskCategoryVO;
import com.planner.model.TaskVO;
import com.planner.permission.TaskPermissionImpl;
import com.planner.repository.TaskCategoryRepository;
import com.planner.repository.TaskRepository;
import com.planner.repository.UserRepository;
import com.planner.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

  @Autowired
  private TaskCategoryRepository taskCategoryRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private TaskMapStructFactory taskMapStructFactory;

  @Autowired
  private TaskPermissionImpl taskPermission;

  @Override
  public TaskVO fetchTasksByTaskId(Users user, Long taskId, Long taskCategoryId) {
    return taskMapStructFactory.createTaskVO(
        taskPermission.canViewTask(user, taskId, taskCategoryId));
  }

  @Override
  public TaskCategoryVO createTask(CreateTaskVo createTaskVo, Users users) {
    Long userTaskCategory = Long.valueOf(createTaskVo.getTaskCategoryId());
    TaskCategory taskCategory = taskCategoryRepository.findByUsersAndId(users.getId(),
        userTaskCategory).orElseThrow(TaskCategoryNotFoundException::new);
    Task newTask = taskMapStructFactory.createTaskEntityForPersisting(createTaskVo);
    taskCategory.addTask(newTask);
    return taskMapStructFactory.createVo(taskCategoryRepository.save(taskCategory));
  }

  @Override
  public void removeTask(Users user, Long taskId, Long taskCategoryId) {
    Task task = taskPermission.canViewTask(user, taskId, taskCategoryId);
    TaskCategory taskCategory = taskCategoryRepository.getById(task.getTaskCategory());
    taskCategory.removeTask(task);
    taskCategoryRepository.save(taskCategory);

  }

}
