package com.learn.springmvc.service;

import static com.learn.springmvc.model.Ticket.Category;

import com.learn.springmvc.model.Event;
import com.learn.springmvc.model.Ticket;
import com.learn.springmvc.model.User;
import java.util.List;

public interface TicketService {

  Ticket create(long userId, long eventId, Category category, int place);

  List<Ticket> getByUser(User user);

  List<Ticket> getByEvent(Event event);

  boolean delete(long id);
}
