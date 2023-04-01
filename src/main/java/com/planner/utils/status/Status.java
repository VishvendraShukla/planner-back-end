package com.planner.utils.status;

import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public enum Status {
  DONE("D"),
  ACTIVE("A"),
  CREATED("C"),
  INACTIVE("IA")
  ;

  public String value;
  Status(String value) {
    this.value = value;
  }

  private String getValue(){
    return value;
  }

  @Converter
  public static class StatusConverter implements AttributeConverter<Status,String> {

    @Override
    public String convertToDatabaseColumn(Status attribute) {
      return attribute == null? null: attribute.getValue();
    }

    @Override
    public Status convertToEntityAttribute(String s) {
      return Stream.of(Status.values()).filter(v -> v.getValue().equals(s)).findFirst().orElse(null);
    }
  }
}
