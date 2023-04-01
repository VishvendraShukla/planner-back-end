package com.planner.factory;

import com.planner.entities.Task;
import com.planner.entities.TaskCategory;
import com.planner.model.CreateTaskVo;
import com.planner.model.TaskCategoryVO;
import com.planner.model.TaskVO;
import java.util.List;

public interface TaskMapStructFactory {

  TaskCategory createEntityForPersisting(Long id, TaskCategoryVO taskCategoryVO);

  TaskCategoryVO createVo(TaskCategory taskCategory);

  List<TaskCategoryVO> createListOfVo(List<TaskCategory> taskCategoryList);

  Task createTaskEntityForPersisting(CreateTaskVo taskVO);

  TaskVO createTaskVO(Task task);
}
