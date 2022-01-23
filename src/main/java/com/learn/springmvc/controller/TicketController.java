package com.learn.springmvc.controller;

import com.learn.springmvc.facade.BookingFacade;
import com.learn.springmvc.model.Event;
import com.learn.springmvc.model.Ticket;
import com.learn.springmvc.model.User;
import com.learn.springmvc.util.Utils;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/tickets")
public class TicketController {

  public static final String TICKET_MODEL = "ticketModel";
  private final BookingFacade bookingFacade;

  public TicketController(BookingFacade bookingFacade) {
    this.bookingFacade = bookingFacade;
  }

  @PostMapping("/newTicket")
  public ResponseEntity<HttpStatus> bookTicket(@RequestParam long userId,
      @RequestParam long eventId,
      @RequestParam int place,
      @RequestParam String category) {

    bookingFacade.bookTicket(userId, eventId, place, Utils.parseCategory(category));
    return ResponseEntity.ok(HttpStatus.CREATED);
  }

  @GetMapping("/getByUser")
  public ModelAndView getTicketsByUser(@RequestBody User user,
      @RequestParam(required = false, defaultValue = "25") int pageSize,
      @RequestParam(required = false, defaultValue = "1") int pageNum) {

    var modelAndView = getModelAndView();
    List<Ticket> tickets = bookingFacade.getBookedTickets(user, pageSize, pageNum);
    modelAndView.addObject(TICKET_MODEL, tickets);
    return modelAndView;
  }

  @GetMapping("/getByEvent")
  public ModelAndView getTicketsByEvent(@RequestBody Event event,
      @RequestParam(required = false, defaultValue = "25") int pageSize,
      @RequestParam(required = false, defaultValue = "1") int pageNum) {

    var modelAndView = getModelAndView();
    List<Ticket> tickets = bookingFacade.getBookedTickets(event, pageSize, pageNum);
    modelAndView.addObject(TICKET_MODEL, tickets);
    return modelAndView;
  }

  @DeleteMapping("/delete/{id}")
  public ModelAndView cancelTicket(@PathVariable long id) {

    var modelAndView = getModelAndView();
    var isDeleted = bookingFacade.cancelTicket(id);
    if (isDeleted) {
      modelAndView.addObject(TICKET_MODEL, "Ticket id=" + id + " canceled successfully");
    } else {
      modelAndView.addObject(TICKET_MODEL, "Ticket id=" + id + " not found");
    }

    return modelAndView;
  }

  private ModelAndView getModelAndView() {
    return new ModelAndView("ticketPage");
  }
}
