package com.planner.service.impl;

import com.planner.entities.TaskCategory;
import com.planner.exception.taskcategory.TaskCategoryNotFoundException;
import com.planner.exception.user.UserDataNotFoundException;
import com.planner.factory.TaskMapStructFactory;
import com.planner.model.TaskCategoryVO;
import com.planner.repository.TaskCategoryRepository;
import com.planner.repository.UserRepository;
import com.planner.service.TaskCategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskCategoryServiceImpl implements TaskCategoryService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TaskCategoryRepository taskCategoryRepository;

  @Autowired
  private TaskMapStructFactory taskMapStructFactory;

  @Override
  public TaskCategoryVO createTaskCategory(Long id, TaskCategoryVO taskCategoryVO) {
    TaskCategory savedCategory = taskCategoryRepository.save(
        taskMapStructFactory.createEntityForPersisting(id, taskCategoryVO));
    return taskMapStructFactory.createVo(savedCategory);
  }

  @Override
  public List<TaskCategoryVO> fetchTaskCategoryByUserId(Long userId) {
    List<TaskCategory> taskCategoryList = taskCategoryRepository.findAllByUsers(userId);
    if (taskCategoryList.isEmpty()) {
      throw new UserDataNotFoundException("Nothing Found");
    }
    return taskMapStructFactory.createListOfVo(taskCategoryList);
  }

  @Override
  public boolean removeTaskCategory(Long taskCategoryId) {
    if(!taskCategoryRepository.existsById(taskCategoryId)){
      throw new TaskCategoryNotFoundException();
    }
    taskCategoryRepository.deleteById(taskCategoryId);
    return true;
  }


}
