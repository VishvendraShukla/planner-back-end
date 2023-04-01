package com.planner.service;

import com.planner.entities.Users;
import com.planner.model.CreateTaskVo;
import com.planner.model.TaskCategoryVO;
import com.planner.model.TaskVO;

public interface TaskService {

  TaskVO fetchTasksByTaskId(Users user, Long taskId, Long taskCategoryId);

  TaskCategoryVO createTask(CreateTaskVo createTaskVo, Users users);

  void removeTask(Users user, Long taskId, Long taskCategoryId);

}
