package com.planner.entities;

import com.planner.utils.Id;
import java.util.Calendar;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "task_category")
public class TaskCategory extends Id {

  @Column(name = "category_type")
  private String categoryType;
  @Column(name = "category_name")
  private String categoryName;
  @Column(name = "user_id")
  private Long users;
  @OneToMany(cascade = CascadeType.ALL,
      orphanRemoval = true)
  private Set<Task> task;
  @Column(name = "modified_date")
  private Calendar modifiedDate;
  @Column(name = "creation_date")
  private Calendar creationDate;

  public void addTask(Task newTask) {
    task.add(newTask);
    newTask.setTaskCategory(this.getId());
  }

  public void removeTask(Task taskToRemove) {
    task.remove(taskToRemove);
    taskToRemove.setTaskCategory(null);
  }

}
