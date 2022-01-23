package com.learn.springmvc.service.impl;

import com.learn.springmvc.dao.impl.TicketDao;
import com.learn.springmvc.model.Event;
import com.learn.springmvc.model.Ticket;
import com.learn.springmvc.model.User;
import com.learn.springmvc.model.impl.TicketImpl;
import com.learn.springmvc.service.TicketService;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {

  @Autowired
  TicketDao ticketDao;

  @SneakyThrows
  @Override
  public Ticket create(long userId, long eventId, Ticket.Category category, int place) {
    return ticketDao.save(new TicketImpl(eventId, userId, category, place));
  }

  @Override
  public List<Ticket> getByUser(User user) {
    return ticketDao.getByUser(user);
  }

  @Override
  public List<Ticket> getByEvent(Event event) {
    return ticketDao.getByEvent(event);
  }

  @Override
  public boolean delete(long id) {
    return ticketDao.delete(id);
  }
}
