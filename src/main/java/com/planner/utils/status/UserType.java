package com.planner.utils.status;

import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public enum UserType {
  ADMINISTRATOR("A"),
  MEMBER("M");

  public String value;

  UserType(String value){
    this.value = value;
  }

  public String getValue(){
    return value;
  }

  @Converter
  public static class UserTypeConverter implements AttributeConverter<UserType,String> {

    @Override
    public String convertToDatabaseColumn(UserType attribute) {
      return attribute == null? null: attribute.getValue();
    }

    @Override
    public UserType convertToEntityAttribute(String s) {
      return Stream.of(UserType.values()).filter(v -> v.getValue().equals(s)).findFirst().orElse(null);
    }
  }
}
