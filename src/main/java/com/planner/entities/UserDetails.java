package com.planner.entities;

import com.planner.utils.Id;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_details")
public class UserDetails extends Id {

  @Column(name = "username", nullable = false, unique = true)
  private String username;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "middle_name")
  private String middleName;
  @Column(name = "last_name")
  private String lastName;
  @Column(name = "email")
  private String email;
  @Column(name = "date_of_birth")
  private Calendar dateOfBirth;
  @Column(name = "address")
  private String address;
  @Column(name = "city")
  private String city;
  @Column(name = "country")
  private String country;
  @Column(name = "phone_no", unique = true)
  private String mobile;
  @Column(name = "modified_date")
  private Calendar modifiedDate;
  @Column(name = "creation_date")
  private Calendar creationDate;
}
