package com.planner.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlannerDateFormatter {

  public static final String DD_MM_YYYY = "dd/mm/yyy";

  private final Logger logger = LoggerFactory.getLogger(getClass());

  public static Date convertToDate(String data, String format) {
    DateFormat dateFormat = new SimpleDateFormat(format);
    Date date = null;
    try {
      date = dateFormat.parse(data);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  public static Calendar convertToCalendar(String data, String format) {
    Calendar now = Calendar.getInstance();
    DateFormat dateFormat = new SimpleDateFormat(format);
    Date date = null;
    try {
      date = dateFormat.parse(data);
      now.setTime(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return now;
  }

  public static String convertToYYYYMMDD(Calendar calendar) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    return format.format(calendar.getTime());
  }
}
