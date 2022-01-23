package com.learn.springmvc.model;

import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public interface Event {

  /**
   * Event id. UNIQUE.
   *
   * @return Event Id
   */
  long getId();

  void setId(long id);

  String getTitle();

  void setTitle(String title);

  Date getDate();

  void setDate(Date date);
}
