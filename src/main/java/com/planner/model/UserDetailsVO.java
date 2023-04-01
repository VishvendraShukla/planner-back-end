package com.planner.model;

import com.planner.utils.IdVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsVO extends IdVO {

  public String userName;
  public String firstName;
  public String middleName;
  public String lastName;
  public String email;
  public String dob;
  public String address;
  public String city;
  public String country;
  public String mobileNumber;

  public String toString() {
    return "UserDetailsVO(userName=" + this.getUserName() + ", firstName=" + this.getFirstName()
        + ", middleName=" + this.getMiddleName() + ", lastName=" + this.getLastName() + ", email="
        + this.getEmail() + ", dob=" + this.getDob() + ", address=" + this.getAddress() + ", city="
        + this.getCity() + ", country=" + this.getCountry() + ", mobileNumber="
        + this.getMobileNumber() + ")";
  }
}
