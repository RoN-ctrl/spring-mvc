package com.learn.springmvc.dao.impl;

import com.learn.springmvc.dao.Dao;
import com.learn.springmvc.exception.IdAlreadyExistsException;
import com.learn.springmvc.model.User;
import com.learn.springmvc.model.impl.UserImpl;
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
public class UserDao implements Dao<User> {

  private final Map<Long, User> users = new HashMap<>();

  @Value("${p.users}")
  String filePath;

  @PostConstruct
  @SneakyThrows
  public void populateUsers() {
    List<String> usersFromFile = Utils.readLines(filePath);
    int indexForName = "name".length() + 1;
    int indexForEmail = "email".length() + 1;

    for (String user : usersFromFile) {
      String[] split = user.split(",");
      if (split.length == 2) {
        save(new UserImpl(split[0].substring(indexForName), split[1].substring(indexForEmail)));
      }
    }
  }

  @Override
  public User save(User user) throws IdAlreadyExistsException {
    if (users.containsKey(user.getId())) {
      throw new IdAlreadyExistsException("User with id=" + user.getId() + " already exists");
    }
    users.put(user.getId(), user);
    return user;
  }

  @Override
  public Optional<User> getById(long id) {
    return Optional.ofNullable(users.get(id));
  }

  public Optional<User> getByEmail(String email) {
    return users.values().stream()
        .filter(u -> Objects.equals(email, u.getEmail()))
        .findFirst();
  }

  public List<User> getByName(String name) {
    return users.values().stream()
        .filter(u -> Objects.equals(name, u.getName()))
        .collect(Collectors.toList());
  }

  @Override
  public Optional<User> update(User user) {
    return Optional.ofNullable(users.replace(user.getId(), user));
  }

  @Override
  public boolean delete(long id) {
    if (!users.containsKey(id)) {
      return false;
    }
    users.remove(id);
    return !users.containsKey(id);
  }
}
