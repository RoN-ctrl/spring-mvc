package com.learn.springmvc.dao.impl;

import com.learn.springmvc.dao.Dao;
import com.learn.springmvc.exception.IdAlreadyExistsException;
import com.learn.springmvc.model.Event;
import com.learn.springmvc.model.Ticket;
import com.learn.springmvc.model.User;
import com.learn.springmvc.model.impl.TicketImpl;
import com.learn.springmvc.util.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TicketDao implements Dao<Ticket> {

  private final Map<Long, Ticket> tickets = new HashMap<>();

  @Value("${p.tickets}")
  String filePath;

  @PostConstruct
  @SneakyThrows
  public void populateUsers() {
    List<String> usersFromFile = Utils.readLines(filePath);

    for (String user : usersFromFile) {
      String[] split = user.split(",");
      if (split.length == 4) {
        long eventId = Long.parseLong(split[0].substring("event_id".length() + 1));
        long userId = Long.parseLong(split[1].substring("user_id".length() + 1));
        Ticket.Category category = Utils.parseCategory(split[2].substring("category".length() + 1));
        int place = Integer.parseInt(split[3].substring("place".length() + 1));

        save(new TicketImpl(eventId, userId, category, place));
      }
    }
  }

  @Override
  public Ticket save(Ticket ticket) throws IdAlreadyExistsException {
    if (tickets.containsKey(ticket.getId())) {
      throw new IdAlreadyExistsException("Ticket with id=" + ticket.getId() + " already exists");
    }
    tickets.put(ticket.getId(), ticket);
    return ticket;
  }

  @Override
  public Optional<Ticket> getById(long id) {
    return Optional.ofNullable(tickets.get(id));
  }

  public List<Ticket> getByUser(User user) {
    return tickets.values().stream()
        .filter(t -> Objects.equals(user.getId(), t.getUserId()))
        .collect(Collectors.toList());
  }

  public List<Ticket> getByEvent(Event event) {
    return tickets.values().stream()
        .filter(t -> Objects.equals(event.getId(), t.getEventId()))
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Ticket> update(Ticket ticket) {
    return Optional.ofNullable(tickets.replace(ticket.getId(), ticket));
  }

  @Override
  public boolean delete(long id) {
    tickets.remove(id);
    return !tickets.containsKey(id);
  }
}
