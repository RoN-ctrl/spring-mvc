package com.learn.springmvc.model.impl;

import com.learn.springmvc.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketImpl implements Ticket {

  private static long idCounter = 0;

  private long id;
  private long eventId;
  private long userId;
  private Category category;
  private int place;

  public TicketImpl(long eventId, long userId, Category category, int place) {
    this.id = ++idCounter;
    this.eventId = eventId;
    this.userId = userId;
    this.category = category;
    this.place = place;
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
  public long getEventId() {
    return eventId;
  }

  @Override
  public void setEventId(long eventId) {
    this.eventId = eventId;
  }

  @Override
  public long getUserId() {
    return userId;
  }

  @Override
  public void setUserId(long userId) {
    this.userId = userId;
  }

  @Override
  public Category getCategory() {
    return category;
  }

  @Override
  public void setCategory(Category category) {
    this.category = category;
  }

  @Override
  public int getPlace() {
    return place;
  }

  @Override
  public void setPlace(int place) {
    this.place = place;
  }
}
