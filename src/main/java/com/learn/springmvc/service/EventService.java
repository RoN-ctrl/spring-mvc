package com.learn.springmvc.service;

import com.learn.springmvc.model.Event;
import java.util.Date;
import java.util.List;

public interface EventService {

  Event create(String title, Date date);

  Event getById(long id);

  List<Event> getByTitle(String title);

  List<Event> getByDay(Date day);

  Event update(long id, String title, Date date);

  boolean deleteById(long id);
}
