package com.learn.springmvc.util;

import com.learn.springmvc.facade.BookingFacade;
import com.learn.springmvc.model.Event;
import java.text.SimpleDateFormat;
import lombok.SneakyThrows;

public class TestUtils {

  public static final String EVENT_TITLE = "Giselle";
  public static final String EVENT_DATE = "20-02-2021 18:00";
  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");

  @SneakyThrows
  public static Event createTestEvent(BookingFacade bookingFacade) {
    return bookingFacade.createEvent(EVENT_TITLE, DATE_FORMAT.parse(EVENT_DATE));
  }

}
