package com.learn.springmvc.controller;

import static com.learn.springmvc.util.ControllerUtils.getModelAndView;

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
  public static final String TICKET_PAGE = "ticketPage";
  private final BookingFacade bookingFacade;

  public TicketController(BookingFacade bookingFacade) {
    this.bookingFacade = bookingFacade;
  }

  @PostMapping("/newTicket")
  public ModelAndView bookTicket(@RequestParam long userId,
      @RequestParam long eventId,
      @RequestParam int place,
      @RequestParam String category) {

    var modelAndView = getModelAndView(TICKET_PAGE);
    var ticket = bookingFacade.bookTicket(userId, eventId, place, Utils.parseCategory(category));
    modelAndView.addObject(TICKET_MODEL, "BOOKED " + ticket);
    return modelAndView;
  }

  @GetMapping("/getByUser")
  public ModelAndView getTicketsByUser(@RequestBody User user,
      @RequestParam(required = false, defaultValue = "25") int pageSize,
      @RequestParam(required = false, defaultValue = "1") int pageNum) {

    var modelAndView = getModelAndView(TICKET_PAGE);
    List<Ticket> tickets = bookingFacade.getBookedTickets(user, pageSize, pageNum);
    modelAndView.addObject(TICKET_MODEL, tickets);
    return modelAndView;
  }

  @GetMapping("/getByEvent")
  public ModelAndView getTicketsByEvent(@RequestBody Event event,
      @RequestParam(required = false, defaultValue = "25") int pageSize,
      @RequestParam(required = false, defaultValue = "1") int pageNum) {

    var modelAndView = getModelAndView(TICKET_PAGE);
    List<Ticket> tickets = bookingFacade.getBookedTickets(event, pageSize, pageNum);
    modelAndView.addObject(TICKET_MODEL, tickets);
    return modelAndView;
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<HttpStatus> cancelTicket(@PathVariable long id) {

    bookingFacade.cancelTicket(id);
    return new ResponseEntity("CANCELED", HttpStatus.OK);
  }
}
