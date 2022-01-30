package com.learn.springmvc.controller;

import static com.learn.springmvc.util.TestUtils.DATE_FORMAT;
import static com.learn.springmvc.util.TestUtils.EVENT_DATE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.learn.springmvc.facade.BookingFacade;
import com.learn.springmvc.model.Event;
import com.learn.springmvc.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private BookingFacade bookingFacade;

  @Test
  void getEventByIdTest() throws Exception {
    Event event = TestUtils.createTestEvent(bookingFacade);
    mvc.perform(get("/events/getById/{id}", event.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("eventPage"))
        .andExpect(model().attributeExists("eventModel"))
        .andExpect(model().attribute("eventModel", bookingFacade.getEventById(event.getId())));
  }

  @Test
  void getEventByTitleTest() throws Exception {
    Event event = TestUtils.createTestEvent(bookingFacade);
    mvc.perform(get("/events/getByTitle/{title}", event.getTitle()))
        .andExpect(status().isOk())
        .andExpect(view().name("eventPage"))
        .andExpect(model().attributeExists("eventModel"))
        .andExpect(model().attribute("eventModel", bookingFacade.getEventsByTitle(event.getTitle(), 10, 1)));
  }

  @Test
  void getEventForDayTest() throws Exception {
    TestUtils.createTestEvent(bookingFacade);

    mvc.perform(get("/events/getForDay/{day}", "20-02-2021"))
        .andExpect(status().isOk())
        .andExpect(view().name("eventPage"))
        .andExpect(model().attributeExists("eventModel"))
        .andExpect(
            model().attribute("eventModel", bookingFacade.getEventsForDay(DATE_FORMAT.parse(EVENT_DATE), 10, 1)));
  }

  @Test
  void createEventTest() throws Exception {
    mvc.perform(post("/events/create?title=testEvent&date=20-02-2021"))
        .andExpect(status().isOk());
  }

  @Test
  void updateEventTest() throws Exception {
    Event testEvent = TestUtils.createTestEvent(bookingFacade);
    mvc.perform(post("/events/update/" + testEvent.getId() + "?title=testEvent&date=20-02-2021"))
        .andExpect(status().isOk());
  }

  @Test
  void deleteEventTest() throws Exception {
    Event testEvent = TestUtils.createTestEvent(bookingFacade);

    mvc.perform(delete("/events/delete/{id}", testEvent.getId()))
        .andExpect(status().isOk())
        .andReturn();
  }
}