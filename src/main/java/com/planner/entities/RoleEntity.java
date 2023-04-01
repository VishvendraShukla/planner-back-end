package com.planner.entities;

import com.planner.utils.Id;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class RoleEntity extends Id {


  @Column(name = "name")
  private String name;

  @ManyToMany(mappedBy = "roleEntities")
  private Set<Users> users;

}
