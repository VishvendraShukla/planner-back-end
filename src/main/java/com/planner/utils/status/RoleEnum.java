package com.planner.utils.status;

import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public enum RoleEnum {
  ROLE_USER("ROLE_USER"),
  ROLE_ADMIN("ROLE_ADMIN"),
  ROLE_DASH_USER("ROLE_DASH_USER");


  public String value;
  RoleEnum(String value) {
    this.value = value;
  }

  private String getValue(){
    return value;
  }

  @Converter
  public static class RoleConverter implements AttributeConverter<RoleEnum,String> {

    @Override
    public String convertToDatabaseColumn(RoleEnum attribute) {
      return attribute == null? null: attribute.getValue();
    }

    @Override
    public RoleEnum convertToEntityAttribute(String s) {
      return Stream.of(RoleEnum.values()).filter(v -> v.getValue().equals(s)).findFirst().orElse(null);
    }
  }
}
