package com.planner.entities;

import com.planner.utils.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_type")
public class UserType extends Id {

  @Column(name = "user_type")
  private String userType;
  @Column(name = "user_type_enum")
  private String userTypeEnum;
  @Column(name = "auth_id")
  private Long authId;

}
