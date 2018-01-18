package codeu.model.store.basic;

import codeu.model.data.Conversation;
import codeu.model.store.agent.PersistentStorageAgent;
import codeu.model.store.basic.BasicDefaultDataStore;
import codeu.model.store.interfaces.ConversationStore;
import java.util.ArrayList;
import java.util.List;

/**
 * Basic implementation of ConversationStore that uses in-memory data structures to hold values and
 * automatically loads from and saves to PersistentStorageAgent. It's a singleton so different
 * servlet classes can access the same instance.
 */
public class BasicConversationStore implements ConversationStore {

  /**
   * Singleton instance of BasicConversationStore.
   */
  private static BasicConversationStore instance;

  /**
   * Returns the singleton instance of BasicConversationStore that should be shared between all
   * servlet classes. Do not call this function from a test; use getTestInstance() instead.
   */
  public static BasicConversationStore getInstance() {
    if(instance == null) {
      instance = new BasicConversationStore(PersistentStorageAgent.getInstance());
      instance.loadFromStorage();
    }
    return instance;
  }

  /**
   * The PersistentStorageAgent responsible for loading Conversations from and saving Conversations
   * to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /**
   * The in-memory list of Conversations.
   */
  private List<Conversation> conversations;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private BasicConversationStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    conversations = new ArrayList<>();
  }

  /**
   * Load Conversation objects from persistent storage.
   * Returns true if the load succeeded. Otherwise conversations will be empty.
   */
  private boolean loadFromStorage() {
    // If loading from storage clear existing objects.
    boolean loaded = false;
    conversations = new ArrayList<>();
    if (persistentStorageAgent.isValid()) {
      try {
        conversations.addAll(persistentStorageAgent.getAllConversations());
        loaded = true;
      } catch (Exception e) {
        loaded = false;
        System.err.println("ERROR: Unable to load from Datastore (conversations).");
      }
    }
    return loaded;
  }

  /**
   * Load a set of randomly-generated Conversation objects.
   * Returns false if a error occurs.
   */
  public boolean loadTestData() {
    boolean loaded = false;
    try {
      conversations.addAll(BasicDefaultDataStore.getInstance().getAllConversations());
      loaded = true;
    } catch (Exception e) {
      loaded = false;
      System.err.println("ERROR: Unable to establish initial store (conversations).");
    }
    return loaded;
  }

  /**
   * Access the current set of conversations known to the application.
   */
 @Override
  public List<Conversation> getAllConversations() {
    return conversations;
  }

  /**
   * Add a new conversation to the current set of conversations known to the application.
   */
  @Override
  public void addConversation(Conversation conversation) {
    conversations.add(conversation);
    persistentStorageAgent.writeThrough(conversation);
  }

  /**
   * Check whether a Conversation title is already known to the application.
   */
  @Override
  public boolean isTitleTaken(String title) {
    // This approach will be pretty slow if we have many Conversations.
    for (Conversation conversation : conversations) {
      if (conversation.getTitle().equals(title)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Find and return the Conversation with the given title.
   */
  @Override
  public Conversation getConversationWithTitle(String title) {
    for (Conversation conversation : conversations) {
      if (conversation.getTitle().equals(title)) {
        return conversation;
      }
    }
    return null;
  }
}
