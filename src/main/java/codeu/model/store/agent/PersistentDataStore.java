package codeu.model.store.agent;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.agent.PersistentDataStoreException;
import codeu.model.store.interfaces.InitialDataStore;
import codeu.model.store.interfaces.InitialDataStoreException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * This class handles all interactions with Google App Engine's Datastore service.
 * On startup it sets the state of the applications's data objects from the current contents of
 * its Datastore. It also performs writes of new of modified objects back to the Datastore.
 *
 * This class or BasicDefaultDataStore should be used to establish the initial set of users,
 * conversations, and messages known to the system.
 */
public class PersistentDataStore implements InitialDataStore {

  // Handle to Google AppEngine's Datastore service.
  private DatastoreService datastore;

  private final List<User> users = new ArrayList<>();
  private final List<Conversation> conversations = new ArrayList<>();
  private final List<Message> messages = new ArrayList<>();

  // State of Datastore load
  //   ready - Datastore has been loaded; no errors were detected.
  //   loading - Datastore load has been initiated, but is not yet completed.
  //   loadFailed - Datastore load completed with an error. The internal state
  //     of the database is unknown.
  //   error - An error has been detected during Datastore load. The error
  //     information will be captured in errorString.
  private boolean ready = false;
  private boolean loading = false;
  private boolean loadFailed = false;
  private boolean error = false;
  private String errorString;

  public PersistentDataStore() {
    System.out.println("PersistentDataStore ctor entered.");
  }

  /**
   * Set up state to begin loading objects from the Datastore service.
   */
  public void setupLoad() {
    datastore = DatastoreServiceFactory.getDatastoreService();
    loading = true;
    ready = false;
    System.out.println("PersistentDataStore datastore loading.");
  }

  public boolean isLoading() {
    return loading;
  }

  public boolean isReady() {
    return ready;
  }

  /**
   * Update state to indicate that all objects have been loaded from the
   * Datastore service.
   */
  public void setReady() {
    ready = true;
    loading = false;
    System.out.println("PersistentDataStore datastore load complete.");
  }

  /**
   * Record an error from the Datastore service. Terminate the load process.
   */
  private void setError(String s) {
    ready = false;
    loading = false;
    error = true;
    errorString = s;
    System.out.println("PersistentDataStore datastore error: " + s);
  }

  public boolean hasError() {
    return error;
  }

  public String getError() {
    if (error) {
      return errorString;
    } else {
      return null;
    }
  }

  /**
   * Return true if all objects have been successfully retrieved from
   * the Datastore service.
   */
  @Override
  public boolean isValid() {
    return (isReady() && !hasError() && !loadFailed);
  }

  /**
   * Retrieve all User objects from the Datastore service.
   * The returned list may be empty.
   * If an error was detected during the load from the Datastore service,
   * throw a new PersistentDataStoreException.
   */
  @Override
  public List<User> getAllUsers() throws InitialDataStoreException {
    if (!ready) {
      if (loadFailed) {
        throw new PersistentDataStoreException("User Load Failed");
      }
      return null;
    }
    return users;
  }

  /**
   * Retrieve all Conversation objects from the Datastore service.
   * The returned list may be empty.
   * If an error was detected during the load from the Datastore service,
   * throw a new PersistentDataStoreException.
   */
  @Override
  public List<Conversation> getAllConversations() throws InitialDataStoreException {
    if (!ready) {
      if (loadFailed) {
        throw new PersistentDataStoreException("Conversation Load Failed");
      }
      return null;
    }
    return conversations;
  }

  /**
   * Retrieve all Message objects from the Datastore service.
   * The returned list may be empty.
   * If an error was detected during the load from the Datastore service,
   * throw a new PersistentDataStoreException.
   */
  @Override
  public List<Message> getAllMessages() throws InitialDataStoreException {
    if (!ready) {
      if (loadFailed) {
        throw new PersistentDataStoreException("Message Load Failed");
      }
      return null;
    }
    return messages;
  }

  /**
   * Preload all User objects from the Datastore service.
   * The application calls getAllUsers() to access the User objects.
   */
  public void loadUsers() {
    // Retrieve all users from the datastore.
    Query query = new Query("chat-users");
    PreparedQuery results = datastore.prepare(query);

    int bad = 0;
    int added = 0;
    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String)entity.getProperty("uuid"));
        String userName = (String)entity.getProperty("username");
        Instant creationTime = Instant.parse((String)entity.getProperty("creation_time"));
        User user = new User(uuid, userName, creationTime);
        users.add(user);
        added++;
      } catch (Exception e) {
        bad++;
        // TODO: Perform checks for known conditions, especially if ignorable or retryable.
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
      }
    }
    System.out.println("Loaded " + Integer.toString(added) + " users, rejected " +
                       Integer.toString(bad) + " users." );
    if (added == 0 && bad > 0) {
      // If no objects were added and at least one failure was detected, then declare
      // that the load has failed.
      loadFailed = true;
    }
  }

  /**
   * Preload all Conversation objects from the Datastore service.
   * The application calls getAllConversations() to access the User objects.
   */
  public void loadConversations() {
    // Retrieve all conversations from the datastore.
    Query query = new Query("chat-conversations");
    PreparedQuery results = datastore.prepare(query);

    int bad = 0;
    int added = 0;
    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String)entity.getProperty("uuid"));
        UUID ownerUuid = UUID.fromString((String)entity.getProperty("owner_uuid"));
        String title = (String)entity.getProperty("title");
        Instant creationTime = Instant.parse((String)entity.getProperty("creation_time"));
        Conversation conversation = new Conversation(uuid, ownerUuid, title, creationTime);
        conversations.add(conversation);
        added++;
      } catch (Exception e) {
        bad++;
        // TODO: Perform checks for known conditions, especially if ignorable or retryable.
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
      }
    }
    System.out.println("Loaded " + Integer.toString(added) + " conversations, rejected " +
                       Integer.toString(bad) + " conversations.");
    if (added == 0 && bad > 0) {
      // If no objects were added and at least one failure was detected, then declare
      // that the load has failed.
      loadFailed = true;
    }
  }

  /**
   * Preload all Message objects from the Datastore service.
   * The application calls getAllMessages() to access the User objects.
   */
  public void loadMessages() {
    // Retrieve all messages from the datastore.
    Query query = new Query("chat-messages");
    PreparedQuery results = datastore.prepare(query);

    int bad = 0;
    int added = 0;
    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String)entity.getProperty("uuid"));
        UUID conversationUuid = UUID.fromString((String)entity.getProperty("conv_uuid"));
        UUID authorUuid = UUID.fromString((String)entity.getProperty("author_uuid"));
        Instant creationTime = Instant.parse((String)entity.getProperty("creation_time"));
        String content = (String)entity.getProperty("content");
        Message message = new Message(uuid, conversationUuid, authorUuid, content, creationTime);
        messages.add(message);
        added++;
      } catch (Exception e) {
        bad++;
        // TODO: Perform checks for known conditions, especially if ignorable or retryable.
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
      }
    }
    System.out.println("Loaded " + Integer.toString(added) + " messages, rejected " +
                       Integer.toString(bad) + " messages.");
    if (added == 0 && bad > 0) {
      // If no objects were added and at least one failure was detected, then declare
      // that the load has failed.
      loadFailed = true;
    }
  }

  /**
   * Write a User object to the Datastore service.
   * If a User object with the same ID already exists, the new object
   * will replace it.
   */
  public void writeThrough(User user) {

    System.out.println("Adding user");

    Entity userEntity = new Entity("chat-users");
    userEntity.setProperty("uuid", user.getId().toString());
    userEntity.setProperty("username", user.getName());
    userEntity.setProperty("creation_time", user.getCreationTime().toString());
    datastore.put(userEntity);
  }

  /**
   * Write a Message object to the Datastore service.
   * If a Message object with the same ID already exists, the new object
   * will replace it.
   */
  public void writeThrough(Message message) {

    System.out.println("Adding message");

    Entity messageEntity = new Entity("chat-messages");
    messageEntity.setProperty("uuid", message.getId().toString());
    messageEntity.setProperty("conv_uuid",
                              message.getConversationId().toString());
    messageEntity.setProperty("author_uuid", message.getAuthorId().toString());
    messageEntity.setProperty("content", message.getContent());
    messageEntity.setProperty("creation_time",
                              message.getCreationTime().toString());
    datastore.put(messageEntity);
  }

  /**
   * Write a Conversation object to the Datastore service.
   * If a Conversation object with the same ID already exists, the new object
   * will replace it.
   */
  public void writeThrough(Conversation conversation) {

    System.out.println("Adding conversation");

    Entity conversationEntity = new Entity("chat-conversations");
    conversationEntity.setProperty("uuid", conversation.getId().toString());
    conversationEntity.setProperty("owner_uuid",
                                   conversation.getOwnerId().toString());
    conversationEntity.setProperty("title", conversation.getTitle());
    conversationEntity.setProperty("creation_time",
                                   conversation.getCreationTime().toString());
    datastore.put(conversationEntity);
  }
}
