package com.planner.entities;

import com.planner.utils.Id;
import com.planner.utils.status.Status;
import com.planner.utils.status.Status.StatusConverter;
import java.util.Calendar;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task extends Id {

  @Column(name = "description")
  private String description;
  @Column(name = "status")
  @Convert(converter = StatusConverter.class)
  private Status status;
  @Column(name = "due_date")
  private Calendar dueDate;
  @Column(name = "modified_date")
  private Calendar modifiedDate;
  @Column(name = "creation_date")
  private Calendar creationDate;
  @Column(name = "category_id")
  private Long taskCategory;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Task task = (Task) o;
    return getId() != null && Objects.equals(getId(), task.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
