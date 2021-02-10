package com.maktab.bossTicketShop.controller;

import com.maktab.bossTicketShop.entity.Book;
import com.maktab.bossTicketShop.entity.Ticket;
import com.maktab.bossTicketShop.entity.User;
import com.maktab.bossTicketShop.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TicketController {

    private TicketService ticketService ;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @RequestMapping(value = "/confirmServlet")
    public ModelAndView doPostConfirmRequest(HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse){
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        Integer bookId =Integer.valueOf(httpServletRequest.getParameter("btnValue"));
        System.out.println(bookId);
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute("bookId",bookId);
        return new ModelAndView("ConfirmForm");
    }

    @RequestMapping(value = "/buyTicket")
    public ModelAndView doPostBuyTicketRequest(HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse){
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        HttpSession httpSession = httpServletRequest.getSession();
        Integer bookId = (Integer) httpSession.getAttribute("bookId");
        List<Book> books = (List<Book>)httpSession.getAttribute("books");
        Book book = books.stream().filter(book1 -> book1.getId().equals(bookId)).findFirst().get() ;
        User user = (User) httpSession.getAttribute("user");
        String ownerName = httpServletRequest.getParameter("ownerName");
        String gender = httpServletRequest.getParameter("gender");
        ticketService.createNewTicket(book,gender,ownerName,user);
        return new ModelAndView("search");
    }

    @RequestMapping(value = "/ShowUserTicket")
    public ModelAndView doGetUserAllTicketRequest(HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse){
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        HttpSession httpSession = httpServletRequest.getSession();
        User user = (User) httpSession.getAttribute("user");
        List<Ticket> tickets = ticketService.getUserAllTickets(user);
        httpSession.setAttribute("tickets",tickets);
        return new ModelAndView("showAllTicket");
    }

    @RequestMapping(value = "/ViewTicket")
    public ModelAndView doGetTicketDetail(HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse){
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        Integer ticketId = Integer.valueOf(httpServletRequest.getParameter("btnValue"));
        Ticket ticket = ticketService.getTicketById(ticketId);
        Book book = ticket.getBookId() ;
        httpServletRequest.setAttribute("date",book.getDate());
        httpServletRequest.setAttribute("hour",String.valueOf(book.getHour()));
        httpServletRequest.setAttribute("minute",String.valueOf(book.getMinute()));
        httpServletRequest.setAttribute("ownerName",ticket.getOwnerName());
        httpServletRequest.setAttribute("gender",ticket.getGender());
        httpServletRequest.setAttribute("bookid",String.valueOf(book.getId()));
        httpServletRequest.setAttribute("ticketid",String.valueOf(ticket.getId()));
        httpServletRequest.setAttribute("des-city",book.getDestinationCity());
        httpServletRequest.setAttribute("org-city",book.getOriginCity());
        return new ModelAndView("showticket");
    }

    @RequestMapping("/DeleteTicket")
    public ModelAndView doGetDeleteTicketRequest(HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse){
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        System.out.println(httpServletRequest.getParameter("ticket_id"));
        Integer tickectId = Integer.valueOf(httpServletRequest.getParameter("ticket_id"));
        ticketService.deleteTicketById(tickectId);
        return new ModelAndView("search");
    }


}
