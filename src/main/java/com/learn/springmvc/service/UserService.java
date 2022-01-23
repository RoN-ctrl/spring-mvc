package com.learn.springmvc.service;

import com.learn.springmvc.model.User;
import java.util.List;

public interface UserService {

  User create(String name, String email);

  User getById(long id);

  User getByEmail(String email);

  List<User> getByName(String name);

  User update(long id, String name, String email);

  boolean deleteById(long id);
}
