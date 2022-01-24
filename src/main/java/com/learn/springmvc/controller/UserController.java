package com.learn.springmvc.controller;

import static com.learn.springmvc.util.ControllerUtils.getModelAndView;

import com.learn.springmvc.facade.BookingFacade;
import com.learn.springmvc.model.User;
import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {

  public static final String USER_MODEL = "userModel";
  public static final String USER_PAGE = "userPage";
  private final BookingFacade bookingFacade;

  public UserController(BookingFacade bookingFacade) {
    this.bookingFacade = bookingFacade;
  }

  @GetMapping("/getById/{id}")
  public ModelAndView getUserById(@PathVariable long id) {

    var modelAndView = getModelAndView(USER_PAGE);
    var user = bookingFacade.getUserById(id);
    if (Objects.nonNull(user)) {
      modelAndView.addObject(USER_MODEL, user);
    } else {
      modelAndView.addObject(USER_MODEL, "User not found : id=" + id);
    }

    return modelAndView;
  }

  @GetMapping("/getByEmail/{email}")
  public ModelAndView getUserByEmail(@PathVariable String email) {

    var modelAndView = getModelAndView(USER_PAGE);
    var user = bookingFacade.getUserByEmail(email);
    if (Objects.nonNull(user)) {
      modelAndView.addObject(USER_MODEL, user);
    } else {
      modelAndView.addObject(USER_MODEL, "User not found : email=" + email);
    }

    return modelAndView;
  }

  @GetMapping("/getByName/{name}")
  public ModelAndView getUserByName(@PathVariable String name,
      @RequestParam(required = false, defaultValue = "25") int pageSize,
      @RequestParam(required = false, defaultValue = "1") int pageNum) {

    var modelAndView = getModelAndView(USER_PAGE);
    List<User> users = bookingFacade.getUsersByName(name, pageSize, pageNum);
    modelAndView.addObject(USER_MODEL, users);
    return modelAndView;
  }

  @PostMapping("/create")
  public ModelAndView createUser(@RequestParam String name,
      @RequestParam String email) {

    var modelAndView = getModelAndView(USER_PAGE);
    var user = bookingFacade.createUser(name, email);
    modelAndView.addObject(USER_MODEL, "CREATED " + user);
    return modelAndView;
  }

  @PostMapping("/update/{id}")
  public ModelAndView updateUser(@PathVariable long id,
      @RequestParam String name,
      @RequestParam String email) {

    var modelAndView = getModelAndView(USER_PAGE);
    var user = bookingFacade.updateUser(id, name, email);
    modelAndView.addObject(USER_MODEL, "UPDATED " + user);
    return modelAndView;
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<HttpStatus> deleteUser(@PathVariable long id) {

    bookingFacade.deleteUser(id);
    return new ResponseEntity("DELETED", HttpStatus.OK);
  }
}
