package codeu.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import codeu.model.data.User;
import codeu.model.store.basic.BasicUserStore;

/**
 * Servlet class responsible for the login page.
 */
public class LoginServlet  extends HttpServlet {

	/**
	 * This function fires when a user requests the /login URL.
	 * It simply forwards the request to login.jsp.
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request,response);
	}
	
	/**
	 * This function fires when a user submits the login form.
	 * It gets the username from the submitted form data, and then adds
	 * it to the session so we know the user is logged in.
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = request.getParameter("username");
		
		if(!username.matches("[\\w*\\s*]*")){
			request.setAttribute("error", "Please enter only letters, numbers, and spaces.");
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request,response);
			return;
		}
		
		if(!BasicUserStore.getInstance().isUserRegistered(username)){
			User user = new User(UUID.randomUUID(), username, System.currentTimeMillis());
			BasicUserStore.getInstance().addUser(user);
		}
		
		request.getSession().setAttribute("user", username);
		response.sendRedirect("/ChatApp/conversations");
	}
}
