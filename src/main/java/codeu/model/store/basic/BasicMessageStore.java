package codeu.model.store.basic;

import codeu.model.data.Message;
import codeu.model.store.agent.PersistentStorageAgent;
import codeu.model.store.basic.BasicDefaultDataStore;
import codeu.model.store.interfaces.MessageStore;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Basic implementation of MessageStore that uses in-memory data structures to hold values and
 * automatically loads from and saves to PersistentStorageAgent. It's a singleton so different
 * servlet classes can access the same instance.
 */
public class BasicMessageStore implements MessageStore {

  /**
   * Singleton instance of BasicMessageStore.
   */
  private static BasicMessageStore instance;

  /**
   * Returns the singleton instance of BasicMessageStore that should be shared between all servlet
   * classes. Do not call this function from a test; use getTestInstance() instead.
   */
  public static BasicMessageStore getInstance() {
    if(instance == null) {
      instance = new BasicMessageStore(PersistentStorageAgent.getInstance());
      instance.loadFromStorage();
    }
    return instance;
  }

  /**
   * The PersistentStorageAgent responsible for loading Messages from and saving Messages
   * to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /**
   * The in-memory list of Messages.
   */
  private List<Message> messages;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private BasicMessageStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    messages = new ArrayList<>();
  }

  /**
   * Load Message objects from persistent storage.
   * Returns true if the load succeeded. Otherwise messages will be empty.
   */
  private boolean loadFromStorage() {
    // If loading from storage clear existing objects.
    boolean loaded = false;
    messages = new ArrayList<>();
    if (persistentStorageAgent.isValid()) {
      try {
        messages.addAll(persistentStorageAgent.getAllMessages());
        loaded = true;
      } catch (Exception e) {
        loaded = false;
        System.err.println("ERROR: Unable to load from Datastore (messages).");
      }
    }
    return loaded;
  }

  /**
   * Load a set of randomly-generated Message objects.
   * Returns false if a error occurs.
   */
  public boolean loadTestData() {
    boolean loaded = false;
    try {
      messages.addAll(BasicDefaultDataStore.getInstance().getAllMessages());
      loaded = true;
    } catch (Exception e) {
      loaded = false;
      System.out.println("ERROR: Unable to establish initial store (messages).");
    }
    return loaded;
  }

  /**
   * Add a new message to the current set of messages known to the application.
   */
  @Override
  public void addMessage(Message message) {
    messages.add(message);
    persistentStorageAgent.writeThrough(message);
  }

  /**
   * Access the current set of Messages within the given Conversation.
   */
  @Override
  public List<Message> getMessagesInConversation(UUID conversationId) {

    List<Message> messagesInConversation = new ArrayList<>();

    for (Message message : messages) {
      if (message.getConversationId().equals(conversationId)) {
        messagesInConversation.add(message);
      }
    }

    return messagesInConversation;
  }
}
