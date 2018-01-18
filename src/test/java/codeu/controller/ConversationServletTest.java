package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.User;
import codeu.model.store.interfaces.ConversationStore;
import codeu.model.store.interfaces.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class ConversationServletTest {

  private ConversationServlet conversationServlet;
  private HttpServletRequest mockRequest;
  private HttpSession mockSession;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private ConversationStore mockConversationStore;
  private UserStore mockUserStore;

  @Before
  public void setup() {
    conversationServlet = new ConversationServlet();

    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/conversations.jsp"))
        .thenReturn(mockRequestDispatcher);

    mockConversationStore = Mockito.mock(ConversationStore.class);
    conversationServlet.setConversationStore(mockConversationStore);

    mockUserStore = Mockito.mock(UserStore.class);
    conversationServlet.setUserStore(mockUserStore);
  }

  @Test
  public void testDoGet() throws IOException, ServletException {
    List<Conversation> fakeConversationList = new ArrayList<>();
    fakeConversationList.add(new Conversation(UUID.randomUUID(), UUID.randomUUID(),
        "test_conversation", Instant.now()));
    Mockito.when(mockConversationStore.getAllConversations()).thenReturn(fakeConversationList);

    conversationServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("conversations", fakeConversationList);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPost_UserNotLoggedIn() throws IOException, ServletException {
    Mockito.when(mockSession.getAttribute("user")).thenReturn(null);

    conversationServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockConversationStore, Mockito.never())
        .addConversation(Mockito.any(Conversation.class));
    Mockito.verify(mockResponse).sendRedirect("/conversations");
  }

  @Test
  public void testDoPost_InvalidUser() throws IOException, ServletException {
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(null);

    conversationServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockConversationStore, Mockito.never())
        .addConversation(Mockito.any(Conversation.class));
    Mockito.verify(mockResponse).sendRedirect("/conversations");
  }

  @Test
  public void testDoPost_BadConversationName() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("conversationTitle")).thenReturn("bad !@#$% name");
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");

    User fakeUser = new User(UUID.randomUUID(), "test_username", Instant.now());
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(fakeUser);

    conversationServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockConversationStore, Mockito.never())
        .addConversation(Mockito.any(Conversation.class));
    Mockito.verify(mockRequest).setAttribute("error", "Please enter only letters and numbers.");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPost_ConversationNameTaken() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("conversationTitle")).thenReturn("test_conversation");
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");

    User fakeUser = new User(UUID.randomUUID(), "test_username", Instant.now());
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(fakeUser);

    Mockito.when(mockConversationStore.isTitleTaken("test_conversation")).thenReturn(true);

    conversationServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockConversationStore, Mockito.never())
        .addConversation(Mockito.any(Conversation.class));
    Mockito.verify(mockResponse).sendRedirect("/chat/test_conversation");
  }

  @Test
  public void testDoPost_NewConversation() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("conversationTitle")).thenReturn("test_conversation");
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");

    User fakeUser = new User(UUID.randomUUID(), "test_username", Instant.now());
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(fakeUser);

    Mockito.when(mockConversationStore.isTitleTaken("test_conversation")).thenReturn(false);

    conversationServlet.doPost(mockRequest, mockResponse);

    ArgumentCaptor<Conversation> conversationArgumentCaptor =
        ArgumentCaptor.forClass(Conversation.class);
    Mockito.verify(mockConversationStore).addConversation(conversationArgumentCaptor.capture());
    Assert.assertEquals(conversationArgumentCaptor.getValue().getTitle(), "test_conversation");

    Mockito.verify(mockResponse).sendRedirect("/chat/test_conversation");
  }
}