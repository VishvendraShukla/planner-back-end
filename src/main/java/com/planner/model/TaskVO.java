package com.planner.model;

import com.planner.utils.IdVO;
import java.util.Calendar;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskVO extends IdVO {

  private String description;
  private String status;
  private Calendar dueDate;

}
