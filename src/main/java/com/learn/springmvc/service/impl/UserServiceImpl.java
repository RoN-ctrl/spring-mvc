package com.learn.springmvc.service.impl;

import com.learn.springmvc.dao.impl.UserDao;
import com.learn.springmvc.exception.UserNotFoundException;
import com.learn.springmvc.model.User;
import com.learn.springmvc.model.impl.UserImpl;
import com.learn.springmvc.service.UserService;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserDao userDao;

  @SneakyThrows
  @Override
  public User create(String name, String email) {
    return userDao.save(new UserImpl(name, email));
  }

  @Override
  public User getById(long id) {
    return userDao.getById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
  }

  @Override
  public User getByEmail(String email) {
    return userDao.getByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
  }

  @Override
  public List<User> getByName(String name) {
    return userDao.getByName(name);
  }

  @Override
  public User update(long id, String name, String email) {
    User user = getById(id);
    user.setName(name);
    user.setEmail(email);
    return userDao.update(user).orElseThrow();
  }

  @Override
  public boolean deleteById(long id) {
    return userDao.delete(id);
  }
}
