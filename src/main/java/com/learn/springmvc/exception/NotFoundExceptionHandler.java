package com.learn.springmvc.exception;

import com.learn.springmvc.aspect.Loggable;
import com.learn.springmvc.util.ControllerUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class NotFoundExceptionHandler {

  @ExceptionHandler(EventNotFoundException.class)
  @Loggable
  public ModelAndView eventNotFoundHandler(EventNotFoundException e) {
    var modelAndView = ControllerUtils.getModelAndView("errorPage");
    modelAndView.addObject("errorModel", e.getMessage());
    return modelAndView;
  }

  @ExceptionHandler(UserNotFoundException.class)
  @Loggable
  public ModelAndView userNotFoundHandler(UserNotFoundException e) {
    var modelAndView = ControllerUtils.getModelAndView("errorPage");
    modelAndView.addObject("errorModel", e.getMessage());
    return modelAndView;
  }
}
