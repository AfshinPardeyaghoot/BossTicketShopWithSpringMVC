package com.maktab.bossTicketShop.controller;

import com.maktab.bossTicketShop.entity.User;
import com.maktab.bossTicketShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Controller
public class UserController {

    private UserService userService ;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ModelAndView loginController(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = httpServletResponse.getWriter();
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");
        if (username == "" || password == ""){
            writer.println("<script>alert(\" لطفا نام کاربری و رمز ورود را وارد کنید \")</script>");
            httpServletRequest.getRequestDispatcher("index.jsp").include(httpServletRequest,httpServletResponse);
            return null ;
        }
        else {
            if (userService.isValidUser(username,password)){
                HttpSession httpSession = httpServletRequest.getSession();
                httpSession.setAttribute("user", userService.getUserByUsername(username));
                return new ModelAndView("search");
            }else {
                writer.println("<script>alert(\" نام کاربری یا رمز ورود اشتباه است!! \")</script>");
                httpServletRequest.getRequestDispatcher("index.jsp").include(httpServletRequest,httpServletResponse);
                return null;
            }
        }
    }
}
