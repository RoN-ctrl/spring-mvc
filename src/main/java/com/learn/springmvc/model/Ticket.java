package com.learn.springmvc.model;

import org.springframework.stereotype.Component;

@Component
public interface Ticket {

  enum Category {STANDARD, PREMIUM, BAR}

  /**
   * Ticket Id. UNIQUE.
   *
   * @return Ticket Id.
   */
  long getId();

  void setId(long id);

  long getEventId();

  void setEventId(long eventId);

  long getUserId();

  void setUserId(long userId);

  Category getCategory();

  void setCategory(Category category);

  int getPlace();

  void setPlace(int place);

}
