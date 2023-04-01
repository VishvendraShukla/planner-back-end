package com.planner.service;

import com.planner.model.TaskCategoryVO;
import java.util.List;

public interface TaskCategoryService {

  TaskCategoryVO createTaskCategory(Long id, TaskCategoryVO taskCategoryVO);

  List<TaskCategoryVO> fetchTaskCategoryByUserId(Long userId);

  boolean removeTaskCategory(Long taskCategoryId);

}
