package com.example.assignment01;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class Assignment01_2819750Servlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		resp.setContentType("text/html");
		
		UserService us = UserServiceFactory.getUserService();
		User u = us.getCurrentUser();
		String login = us.createLoginURL("/");
		String logout = us.createLogoutURL("/");
		
		req.setAttribute("user", u);
		req.setAttribute("login", login);
		req.setAttribute("logout", logout);
		
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/root.jsp");
		rd.forward(req, resp);
		
	}
}
