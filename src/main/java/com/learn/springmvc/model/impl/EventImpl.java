package com.learn.springmvc.model.impl;

import com.learn.springmvc.model.Event;
import java.util.Date;
import lombok.Data;

@Data
public class EventImpl implements Event {

  private static long idCounter = 0;

  private long id;
  private String title;
  private Date date;

  public EventImpl(String title, Date date) {
    this.id = ++idCounter;
    this.title = title;
    this.date = date;
  }

  @Override
  public long getId() {
    return id;
  }

  @Override
  public void setId(long id) {
    this.id = id;
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public Date getDate() {
    return date;
  }

  @Override
  public void setDate(Date date) {
    this.date = date;
  }
}
