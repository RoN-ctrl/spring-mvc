package com.learn.springmvc.util;

import org.springframework.web.servlet.ModelAndView;

public class ControllerUtils {

  private ControllerUtils() {
  }

  public static ModelAndView getModelAndView(String viewName) {
    return new ModelAndView(viewName);
  }
}
