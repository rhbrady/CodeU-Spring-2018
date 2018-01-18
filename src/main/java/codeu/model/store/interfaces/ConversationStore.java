package codeu.model.store.interfaces;

import codeu.model.data.Conversation;
import java.util.List;

/**
 * This interface defines the minimum set of functions that must be implemented to create a store of
 * Conversation objects.
 */
public interface ConversationStore {

  /** Returns a List containing every Conversation. */
  public List<Conversation> getAllConversations();

  /** Adds a Conversation to the store. */
  public void addConversation(Conversation conversation);

  /** Returns true if a Conversation in this store already has the parameter title. */
  public boolean isTitleTaken(String title);

  /**
   * Returns the Conversation with the parameter title, or null if no matching Conversation is
   * found.
   */
  public Conversation getConversationWithTitle(String title);
}
