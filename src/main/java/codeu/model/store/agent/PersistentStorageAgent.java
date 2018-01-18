package codeu.model.store.agent;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.agent.PersistentDataStore;
import codeu.model.store.interfaces.InitialDataStore;
import codeu.model.store.interfaces.InitialDataStoreException;
import java.util.List;

/**
 * This class is the interface between the application and PersistentDataStore, which
 * handles interactions with Google App Engine's Datastore service.
 *
 * This is a singleton; the single instance is accessed through getInstance().
 * On instantiation, this object performs the initial load of all objects from the Datastore service.
 */
public class PersistentStorageAgent implements InitialDataStore {

  private static PersistentStorageAgent instance;

  private final PersistentDataStore chatStore = new PersistentDataStore();

  /**
   * Access the persistent storage agent, in order to perform object-level loads and/or stores.
   */
  public static PersistentStorageAgent getInstance() {
    if (instance == null) {
      instance = new PersistentStorageAgent();
      instance.start();
    }
    return instance;
  }

  // Private constructor, accessible only through singleton interface
  private PersistentStorageAgent() {
    // Prepare for supporting storage operations from client
  }

  // Implementation of InitialDataStore interface - for getting objects from the store

  /**
   * Return true if all objects have been successfully retrieved from
   * the Datastore service.
   */
  @Override
  public boolean isValid() {
    // Ready to supply data
    return chatStore.isValid();
  }

  /**
   * Retrieve all User objects from the Datastore service.
   * The returned list may be empty.
   * If an error was detected during the load from the Datastore service,
   * throw a new PersistentDataStoreException.
   */
  @Override
  public List<User> getAllUsers() throws InitialDataStoreException {
    return chatStore.getAllUsers();
  }

  /**
   * Retrieve all Conversation objects from the Datastore service.
   * The returned list may be empty.
   * If an error was detected during the load from the Datastore service,
   * throw a new PersistentDataStoreException.
   */
  @Override
  public List<Conversation> getAllConversations() throws InitialDataStoreException {
    return chatStore.getAllConversations();
  }

  /**
   * Retrieve all Message objects from the Datastore service.
   * The returned list may be empty.
   * If an error was detected during the load from the Datastore service,
   * throw a new PersistentDataStoreException.
   */
  @Override
  public List<Message> getAllMessages() throws InitialDataStoreException {
    return chatStore.getAllMessages();
  }

  // methods for reading and writing objects

  /**
   * Write a User object to the Datastore service.
   * If a User object with the same ID already exists, the new object
   * will replace it.
   */
  public void writeThrough(User user) {
    chatStore.writeThrough(user);
  }

  /**
   * Write a Message object to the Datastore service.
   * If a Message object with the same ID already exists, the new object
   * will replace it.
   */
  public void writeThrough(Conversation conversation) {
    chatStore.writeThrough(conversation);
  }

  /**
   * Write a Conversation object to the Datastore service.
   * If a Conversation object with the same ID already exists, the new object
   * will replace it.
   */
  public void writeThrough(Message message) {
    chatStore.writeThrough(message);
  }

  // Start the storage agent.
  // Called once, during object instantiation.
  private void start() {
    chatStore.setupLoad();
    chatStore.loadUsers();
    chatStore.loadConversations();
    chatStore.loadMessages();
    chatStore.setReady();
  }
}
