package com.planner.entities;

import com.planner.utils.Id;
import com.planner.utils.status.UserType;
import com.planner.utils.status.UserType.UserTypeConverter;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username")
})
public class Users extends Id {

  @Column(name = "username", nullable = false ,unique = true)
  private String username;
  @Column(name = "password")
  private String password;
  @Column(name = "is_blocked")
  private boolean isBlocked;
  @Column(name = "salt")
  private String salt;
  @Column(name = "modified_date")
  private Calendar modifiedDate;
  @Column(name = "creation_date")
  private Calendar creationDate;
  @Column(name = "last_login")
  private Calendar lastLogin;
  @Column(name = "user_type")
  @Convert(converter = UserTypeConverter.class)
  private UserType userType;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_details_id")
  private UserDetails userDetails;
  @ManyToMany(cascade = {
      CascadeType.MERGE,
  }, fetch = FetchType.EAGER)
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<RoleEntity> roleEntities = new HashSet<>();

  public void addRole(RoleEntity roleEntity) {
    this.roleEntities.add(roleEntity);
  }

  public void removeRole(RoleEntity roleEntity) {
    this.roleEntities.remove(roleEntity);
  }

  @Override
  public String toString() {
    return "Users{" +
        "username='" + username + '\'' +
        ", isBlocked=" + isBlocked +
        ", modifiedDate=" + modifiedDate +
        ", creationDate=" + creationDate +
        ", lastLogin=" + lastLogin +
        ", userType=" + userType +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Users users = (Users) o;
    return getId() != null && Objects.equals(getId(), users.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}

