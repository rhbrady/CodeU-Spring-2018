package codeu.controller;

import codeu.model.store.agent.PersistentStorageAgent;
import codeu.model.store.basic.BasicConversationStore;
import codeu.model.store.basic.BasicMessageStore;
import codeu.model.store.basic.BasicUserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet class responsible for loading test data.
 */
public class TestDataServlet extends HttpServlet {

  /**
   * Set up state for handling the load test data request.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    // Instantiate the persistent storage agent (if it is not already instantiated).
    // This will start the loading of objects from persistent storage.
    PersistentStorageAgent.getInstance();
  }

  /**
   * This function fires when a user requests the /testdata URL. It simply forwards the request to
   * testdata.jsp.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    request.getRequestDispatcher("/WEB-INF/view/testdata.jsp").forward(request, response);
  }

  /**
   * This function fires when a user submits the testdata form. It reads the confirm/abort
   * buttons and applies the desired action.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String confirmButton = request.getParameter("confirm");

    if (confirmButton != null) {
      BasicUserStore.getInstance().loadTestData();
      BasicConversationStore.getInstance().loadTestData();
      BasicMessageStore.getInstance().loadTestData();
    }

    response.sendRedirect("/");
  }
}
