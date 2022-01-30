package com.learn.springmvc.service.impl;

import com.learn.springmvc.dao.impl.EventDao;
import com.learn.springmvc.exception.EventNotFoundException;
import com.learn.springmvc.model.Event;
import com.learn.springmvc.model.impl.EventImpl;
import com.learn.springmvc.service.EventService;
import java.util.Date;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

  @Autowired
  EventDao eventDao;

  @SneakyThrows
  @Override
  public Event create(String title, Date date) {
    return eventDao.save(new EventImpl(title, date));
  }

  @Override
  public Event getById(long id) {
    return eventDao.getById(id).orElseThrow(() -> new EventNotFoundException("Event not found"));
  }

  @Override
  public List<Event> getByTitle(String title) {
    return eventDao.getByTitle(title);
  }

  @Override
  public List<Event> getByDay(Date day) {
    return eventDao.getByDay(day);
  }

  @Override
  public Event update(long id, String title, Date date) {
    Event event = getById(id);
    event.setTitle(title);
    event.setDate(date);
    return eventDao.update(event).orElseThrow();
  }

  @Override
  public boolean deleteById(long id) {
    return eventDao.delete(id);
  }
}
