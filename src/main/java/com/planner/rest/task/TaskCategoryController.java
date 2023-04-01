package com.planner.rest.task;

import static com.planner.utils.configuration.Constants.ControllerMapping.TASK_CATEGORY;
import static com.planner.utils.configuration.Constants.Urls.CREATE_TASK_CATEGORY;
import static com.planner.utils.configuration.Constants.Urls.GET_TASK_CATEGORY;
import static com.planner.utils.configuration.Constants.Urls.REMOVE_TASK_CATEGORY_BY_ID;

import com.planner.model.TaskCategoryVO;
import com.planner.permission.TaskPermissionImpl;
import com.planner.rest.helper.RestAPIResponse;
import com.planner.rest.response.Response;
import com.planner.service.TaskCategoryService;
import com.planner.utils.access.LoggedUser;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TASK_CATEGORY)
public class TaskCategoryController {

  @Autowired
  private TaskCategoryService taskCategoryService;

  @Autowired
  private TaskPermissionImpl taskPermission;

  @PostMapping(CREATE_TASK_CATEGORY)
  public ResponseEntity<Response> createTaskCategory(@RequestBody TaskCategoryVO taskCategoryVO) {
    TaskCategoryVO taskCategory = taskCategoryService.createTaskCategory(
        LoggedUser.getUser().getId(), taskCategoryVO);
    return RestAPIResponse.successResponse("Task Category Created Successfully");
  }

  @GetMapping(GET_TASK_CATEGORY)
  public ResponseEntity<Response> getTaskCategory() {
    List<TaskCategoryVO> taskCategory = taskCategoryService.fetchTaskCategoryByUserId(
        Long.valueOf(String.valueOf(LoggedUser.getUser().getId())));
    return RestAPIResponse.successResponse(taskCategory);
  }

  @DeleteMapping(REMOVE_TASK_CATEGORY_BY_ID)
  public ResponseEntity<Response> removeTaskCategory(
      @PathVariable("taskCategoryId") String taskCategoryId) {
    taskPermission.isCategoryOwner(LoggedUser.getUser(), Long.valueOf(taskCategoryId));
    taskCategoryService.removeTaskCategory(Long.valueOf(taskCategoryId));
    return RestAPIResponse.successResponse("Removed successfully");
  }

}
