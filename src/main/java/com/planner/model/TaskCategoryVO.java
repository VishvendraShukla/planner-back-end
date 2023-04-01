package com.planner.model;

import com.planner.utils.IdVO;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCategoryVO extends IdVO {

  private String categoryType;
  private String categoryName;
  private List<TaskVO> taskVOList;
}
