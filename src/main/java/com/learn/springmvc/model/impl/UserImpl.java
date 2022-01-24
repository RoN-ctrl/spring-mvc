package com.learn.springmvc.model.impl;

import com.learn.springmvc.model.User;
import lombok.Data;

@Data
public class UserImpl implements User {

  private static long idCounter = 0;

  private long id;
  private String name;
  private String email;

  public UserImpl(String name, String email) {
    this.id = ++idCounter;
    this.name = name;
    this.email = email;
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
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        '}';
  }
}
