package com.maktab.bossTicketShop.controller;

import com.maktab.bossTicketShop.entity.Book;
import com.maktab.bossTicketShop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class BookController {

    private BookService bookService ;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(value = "/search")
    public ModelAndView searchController(HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse) throws Exception{
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = httpServletResponse.getWriter();
        String originCity = httpServletRequest.getParameter("origin_city") ;
        String destination = httpServletRequest.getParameter("destination_city");
        String date = httpServletRequest.getParameter("date");
        httpServletRequest.setAttribute("date",date);
        httpServletRequest.setAttribute("origin_city",originCity);
        httpServletRequest.setAttribute("destination_city",destination);
        List<Book> books = bookService.getBooksByDestinationAndOrgCityAndDate(originCity,destination,date);
        if (books != null){
            HttpSession httpSession = httpServletRequest.getSession();
            httpSession.setAttribute("books",books);
        }else {
            writer.println("<script>alert(\" سفری در تاریخ مشخص شده بین این شهر ها موجود نیست \")</script>");
        }
        return new ModelAndView("search");

    }
}
