package com.learn.springmvc.facade.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.learn.springmvc.config.AppConfig;
import com.learn.springmvc.facade.BookingFacade;
import com.learn.springmvc.model.Event;
import com.learn.springmvc.model.Ticket;
import com.learn.springmvc.model.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class BookingFacadeImplTest {

  private static BookingFacade bookingFacade;

  private static final String EVENT_TITLE = "Giselle";
  private static final String EVENT_DATE = "20/02/2021 18:00";
  private static final String USER_NAME = "Jack";
  private static final String USER_EMAIL = "jack@test.com";
  private static final int PLACE = 17;
  private static final int ZERO = 0;

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

  @BeforeAll
  static void beforeAll() {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    bookingFacade = context.getBean(BookingFacade.class);
  }

  @Test
  void getEventByIdTest() {
    Event event = createTestEvent();
    Event eventById = bookingFacade.getEventById(event.getId());

    assertNotNull(eventById);
    assertEquals(event, eventById);
  }

  @Test
  void getEventsByTitleTest() {
    Event event = createTestEvent();
    List<Event> eventsByTitle = bookingFacade.getEventsByTitle(event.getTitle(), ZERO, ZERO);

    assertNotNull(eventsByTitle);
    assertTrue(eventsByTitle.contains(event));
  }

  @Test
  void getEventsForDayTest() throws ParseException {
    Event event = createTestEvent();
    List<Event> eventsByDay = bookingFacade.getEventsForDay(DATE_FORMAT.parse(EVENT_DATE), ZERO, ZERO);

    assertNotNull(eventsByDay);
    assertTrue(eventsByDay.contains(event));
  }

  @Test
  void createEventTest() {
    Event event = createTestEvent();

    assertNotNull(event);
    assertAll(
        () -> assertEquals(EVENT_TITLE, event.getTitle()),
        () -> assertEquals(EVENT_DATE, DATE_FORMAT.format(event.getDate()))
    );
  }

  @Test
  void updateEventTest() throws ParseException {
    Event event = createTestEvent();
    String updatedTitle = "Updated title";
    String updatedDate = "10/02/2022 10:00";
    Event updatedEvent =
        bookingFacade.updateEvent(event.getId(), updatedTitle, DATE_FORMAT.parse(updatedDate));

    assertNotNull(updatedEvent);
    assertAll(
        () -> assertEquals(updatedTitle, updatedEvent.getTitle()),
        () -> assertEquals(updatedDate, DATE_FORMAT.format(updatedEvent.getDate()))
    );
  }

  @Test
  void deleteEventTest() {
    Event event = createTestEvent();

    assertTrue(bookingFacade.deleteEvent(event.getId()));
    assertFalse(bookingFacade.getEventsByTitle(event.getTitle(), ZERO, ZERO).contains(event));
  }

  @Test
  void getUserByIdTest() {
    User user = createTestUser();
    User userById = bookingFacade.getUserById(user.getId());

    assertNotNull(userById);
    assertEquals(user, userById);
  }

  @Test
  void getUserByEmailTest() {
    User user = createTestUser();
    User userByEmail = bookingFacade.getUserByEmail(user.getEmail());

    assertNotNull(userByEmail);
    assertEquals(user, userByEmail);
  }

  @Test
  void getUsersByNameTest() {
    User user = createTestUser();
    List<User> usersByName = bookingFacade.getUsersByName(user.getName(), ZERO, ZERO);

    assertNotNull(usersByName);
    assertTrue(usersByName.contains(user));
  }

  @Test
  void createUserTest() {
    User user = createTestUser();

    assertNotNull(user);
    assertAll(
        () -> assertEquals(USER_NAME, user.getName()),
        () -> assertEquals(USER_EMAIL, user.getEmail())
    );
  }

  @Test
  void updateUserTest() {
    User user = createTestUser();
    String updatedName = "Updated Jack";
    String updatedEmail = "jach@updated.com";
    User updatedUser = bookingFacade.updateUser(user.getId(), updatedName, updatedEmail);

    assertNotNull(updatedUser);
    assertAll(
        () -> assertEquals(updatedName, updatedUser.getName()),
        () -> assertEquals(updatedEmail, updatedUser.getEmail())
    );
  }

  @Test
  void deleteUserTest() {
    User user = createTestUser();

    assertTrue(bookingFacade.deleteUser(user.getId()));
    assertFalse(bookingFacade.getUsersByName(user.getName(), ZERO, ZERO).contains(user));
  }

  @Test
  void bookTicketTest() {
    User user = createTestUser();
    Event event = createTestEvent();

    Ticket ticket = bookingFacade.bookTicket(user.getId(), event.getId(), PLACE, Ticket.Category.STANDARD);

    assertNotNull(ticket);
    assertAll(
        () -> assertEquals(user.getId(), ticket.getUserId()),
        () -> assertEquals(event.getId(), ticket.getEventId()),
        () -> assertEquals(PLACE, ticket.getPlace()),
        () -> assertEquals(Ticket.Category.STANDARD, ticket.getCategory())
    );
  }

  @Test
  void getBookedTicketsByUserTest() {
    User user = createTestUser();
    Event event = createTestEvent();
    Ticket ticket = bookingFacade.bookTicket(user.getId(), event.getId(), PLACE, Ticket.Category.STANDARD);

    List<Ticket> bookedByUser = bookingFacade.getBookedTickets(user, ZERO, ZERO);

    assertNotNull(bookedByUser);
    assertTrue(bookedByUser.contains(ticket));
  }

  @Test
  void GetBookedTicketsByEventTest() {
    User user = createTestUser();
    Event event = createTestEvent();
    Ticket ticket = bookingFacade.bookTicket(user.getId(), event.getId(), PLACE, Ticket.Category.STANDARD);

    List<Ticket> bookedByEvent = bookingFacade.getBookedTickets(event, ZERO, ZERO);

    assertNotNull(bookedByEvent);
    assertTrue(bookedByEvent.contains(ticket));
  }

  @Test
  void cancelTicketTest() {
    User user = createTestUser();
    Event event = createTestEvent();
    Ticket ticket = bookingFacade.bookTicket(user.getId(), event.getId(), PLACE, Ticket.Category.STANDARD);

    assertTrue(bookingFacade.cancelTicket(ticket.getId()));
    assertFalse(bookingFacade.getBookedTickets(user, ZERO, ZERO).contains(ticket));
  }

  @SneakyThrows
  private Event createTestEvent() {
    return bookingFacade.createEvent(EVENT_TITLE, DATE_FORMAT.parse(EVENT_DATE));
  }

  private User createTestUser() {
    return bookingFacade.createUser(USER_NAME, USER_EMAIL);
  }
}