package com.planner.factory.impl;

import com.planner.entities.Task;
import com.planner.entities.TaskCategory;
import com.planner.factory.TaskMapStructFactory;
import com.planner.model.CreateTaskVo;
import com.planner.model.TaskCategoryVO;
import com.planner.model.TaskVO;
import com.planner.utils.status.Status;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TaskMapStructFactoryImpl implements TaskMapStructFactory {

  @Override
  public TaskCategory createEntityForPersisting(Long id, TaskCategoryVO taskCategoryVO) {
    Calendar currentTime = Calendar.getInstance();
    TaskCategory taskCategory = new TaskCategory();
    taskCategory.setUsers(id);
    taskCategory.setCategoryName(taskCategoryVO.getCategoryName());
    taskCategory.setCategoryType(taskCategoryVO.getCategoryType());
    taskCategory.setCreationDate(currentTime);
    taskCategory.setModifiedDate(currentTime);
    return taskCategory;
  }

  @Override
  public TaskCategoryVO createVo(TaskCategory taskCategory) {
    TaskCategoryVO taskCategoryVO = new TaskCategoryVO();
    taskCategoryVO.setId(taskCategory.getId());
    taskCategoryVO.setCategoryType(taskCategory.getCategoryType());
    taskCategoryVO.setCategoryName(taskCategory.getCategoryName());
    return taskCategoryVO;
  }

  @Override
  public List<TaskCategoryVO> createListOfVo(List<TaskCategory> taskCategoryList) {
    List<TaskCategoryVO> taskCategoryVOList = new ArrayList<>();
    taskCategoryList.forEach(taskCategory -> {
      taskCategoryVOList.add(createTaskAndCategoryVO(taskCategory));
    });
    return taskCategoryVOList;
  }

  @Override
  public Task createTaskEntityForPersisting(CreateTaskVo taskVO) {
    Task newTask = new Task();
    Calendar currentTime = Calendar.getInstance();
    newTask.setCreationDate(currentTime);
    newTask.setModifiedDate(currentTime);
    newTask.setDescription(taskVO.getDescription());
    newTask.setDueDate(taskVO.getDueDate());
    newTask.setStatus(Status.valueOf(taskVO.getStatus()));
    newTask.setId(null);
    return newTask;
  }

  @Override
  public TaskVO createTaskVO(Task task) {
    TaskVO taskVO = new TaskVO();
    taskVO.setId(task.getId());
    taskVO.setDescription(task.getDescription());
    taskVO.setStatus(task.getStatus().value);
    taskVO.setDueDate(task.getDueDate());
    return taskVO;
  }

  private TaskCategoryVO createTaskAndCategoryVO(TaskCategory taskCategory) {
    TaskCategoryVO taskCategoryVO = new TaskCategoryVO();
    List<TaskVO> taskVOList = new ArrayList<>();
    taskCategoryVO.setId(taskCategory.getId());
    taskCategoryVO.setCategoryType(taskCategory.getCategoryType());
    taskCategoryVO.setCategoryName(taskCategory.getCategoryName());
    taskVOList.clear();
    taskCategory.getTask().forEach(task -> {
      taskVOList.add(createTaskVO(task));
    });
    if(!taskVOList.isEmpty()){
      taskCategoryVO.setTaskVOList(taskVOList);
    }
    return taskCategoryVO;

  }


}
