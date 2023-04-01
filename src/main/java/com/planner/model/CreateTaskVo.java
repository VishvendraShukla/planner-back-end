package com.planner.model;

import java.util.Calendar;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTaskVo{
  private String taskCategoryId;
  private String description;
  private Calendar dueDate;
  private String status;
}
