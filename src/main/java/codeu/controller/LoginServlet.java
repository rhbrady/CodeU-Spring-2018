package codeu.controller;

import codeu.model.data.User;
import codeu.model.store.basic.BasicUserStore;
import codeu.model.store.interfaces.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet class responsible for the login page.
 */
public class LoginServlet extends HttpServlet {

  /**
   * Store class that gives access to Users.
   */
  private UserStore userStore;

  /**
   * Set up state for handling login-related requests. This method is only called when running in a
   * server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(BasicUserStore.getInstance());
  }

  /**
   * Sets the UserStore used by this servlet. This method allows us to use a mock as the UserStore
   * for testing. When running normally, the init() function passes this method a real UserStore.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * This function fires when a user requests the /login URL. It simply forwards the request to
   * login.jsp.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
  }

  /**
   * This function fires when a user submits the login form. It gets the username from the submitted
   * form data, and then adds it to the session so we know the user is logged in.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String username = request.getParameter("username");

    if (!username.matches("[\\w*\\s*]*")) {
      request.setAttribute("error", "Please enter only letters, numbers, and spaces.");
      request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
      return;
    }

    if (!userStore.isUserRegistered(username)) {
      User user = new User(UUID.randomUUID(), username, Instant.now());
      userStore.addUser(user);
    }

    request.getSession().setAttribute("user", username);
    response.sendRedirect("/conversations");
  }
}
