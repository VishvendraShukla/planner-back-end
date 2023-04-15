package com.planner.rest.task;

import static com.planner.utils.configuration.Constants.ControllerMapping.TASKS;
import static com.planner.utils.configuration.Constants.Urls.FETCH_TASK_BY_TASK_ID;
import static com.planner.utils.configuration.Constants.Urls.REMOVE_TASK;

import com.planner.model.CreateTaskVo;
import com.planner.model.TaskCategoryVO;
import com.planner.rest.helper.RestAPIResponse;
import com.planner.rest.response.Response;
import com.planner.service.TaskService;
import com.planner.utils.access.LoggedUser;
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
@RequestMapping(TASKS)
public class TaskRestController {

  @Autowired
  private TaskService taskService;

  @PostMapping
  public ResponseEntity<Response> createTask(@RequestBody CreateTaskVo createTaskVo) {
    TaskCategoryVO taskCategoryVO = taskService.createTask(createTaskVo, LoggedUser.getUser());
    return RestAPIResponse.successResponse(taskCategoryVO, "Task Created Successfully");
  }

  @GetMapping(FETCH_TASK_BY_TASK_ID)
  public ResponseEntity<Response> getTaskByCategoryIdList(@PathVariable("taskId") String taskId,
      @PathVariable("taskCategoryId") String taskCategoryId) {
    return RestAPIResponse.successResponse(
        taskService.fetchTasksByTaskId(LoggedUser.getUser(), Long.valueOf(taskId),
            Long.valueOf(taskCategoryId)));
  }

  @DeleteMapping(REMOVE_TASK)
  public ResponseEntity<Response> removeTask(@PathVariable("taskId") String taskId,
      @PathVariable("taskCategoryId") String taskCategoryId) {
    taskService.removeTask(LoggedUser.getUser(), Long.valueOf(taskId),
        Long.valueOf(taskCategoryId));
    return RestAPIResponse.successResponse("remove task successfully");
  }


}
