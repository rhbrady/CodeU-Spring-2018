package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.BasicConversationStore;
import codeu.model.store.basic.BasicMessageStore;
import codeu.model.store.basic.BasicUserStore;
import codeu.model.store.interfaces.ConversationStore;
import codeu.model.store.interfaces.MessageStore;
import codeu.model.store.interfaces.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * Servlet class responsible for the chat page.
 */
public class ChatServlet extends HttpServlet {

  /**
   * Store class that gives access to Conversations.
   */
  private ConversationStore conversationStore;

  /**
   * Store class that gives access to Messages.
   */
  private MessageStore messageStore;

  /**
   * Store class that gives access to Users.
   */
  private UserStore userStore;

  /**
   * Set up state for handling chat requests.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setConversationStore(BasicConversationStore.getInstance());
    setMessageStore(BasicMessageStore.getInstance());
    setUserStore(BasicUserStore.getInstance());
  }

  /**
   * Sets the ConversationStore used by this servlet. This method allows us to use a mock as the
   * ConversationStore for testing. When running normally, the init() function passes this method a
   * BasicConversationStore.
   */
  void setConversationStore(ConversationStore conversationStore) {
    this.conversationStore = conversationStore;
  }

  /**
   * Sets the MessageStore used by this servlet. This method allows us to use a mock as the
   * MessageStore for testing. When running normally, the init() function passes this method a
   * BasicMessageStore.
   */
  void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
  }

  /**
   * Sets the UserStore used by this servlet. This method allows us to use a mock as the
   * UserStore for testing. When running normally, the init() function passes this method a
   * BasicUserStore.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * This function fires when a user navigates to the chat page. It gets the conversation title from
   * the URL, finds the corresponding Conversation, and fetches the messages in that Conversation.
   * It then forwards to chat.jsp for rendering.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String requestUrl = request.getRequestURI();
    String conversationTitle = requestUrl.substring("/chat/".length());

    Conversation conversation = conversationStore.getConversationWithTitle(conversationTitle);
    if (conversation == null) {
      // couldn't find conversation, redirect to conversation list
      System.out.println("Conversation was null: " + conversationTitle);
      response.sendRedirect("/conversations");
      return;
    }

    UUID conversationId = conversation.getId();

    List<Message> messages = messageStore.getMessagesInConversation(conversationId);

    request.setAttribute("conversation", conversation);
    request.setAttribute("messages", messages);
    request.getRequestDispatcher("/WEB-INF/view/chat.jsp").forward(request, response);
  }

  /**
   * This function fires when a user submits the form on the chat page. It gets the logged-in
   * username from the session, the conversation title from the URL, and the chat message from the
   * submitted form data. It creates a new Message from that data, adds it to the model, and then
   * redirects back to the chat page.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    String username = (String) request.getSession().getAttribute("user");
    if (username == null) {
      // user is not logged in, don't let them add a message
      response.sendRedirect("/login");
      return;
    }

    User user = userStore.getUser(username);
    if (user == null) {
      // user was not found, don't let them add a message
      response.sendRedirect("/login");
      return;
    }

    String requestUrl = request.getRequestURI();
    String conversationTitle = requestUrl.substring("/chat/".length());

    Conversation conversation = conversationStore.getConversationWithTitle(conversationTitle);
    if (conversation == null) {
      // couldn't find conversation, redirect to conversation list
      response.sendRedirect("/conversations");
      return;
    }

    String messageContent = request.getParameter("message");

    // this removes any HTML from the message content
    String cleanedMessageContent = Jsoup.clean(messageContent, Whitelist.none());

    Message message =
        new Message(UUID.randomUUID(), conversation.getId(), user.getId(),
                    cleanedMessageContent, Instant.now());

   messageStore.addMessage(message);

    // redirect to a GET request
    response.sendRedirect("/chat/" + conversationTitle);
  }
}
