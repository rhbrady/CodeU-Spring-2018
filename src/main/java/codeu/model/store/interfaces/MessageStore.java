package codeu.model.store.interfaces;

import codeu.model.data.Message;
import java.util.List;
import java.util.UUID;

/**
 * This interface defines the minimum set of functions that must be implemented to create a store of
 * Message objects.
 */
public interface MessageStore {

  /** Adds a Message to the store. */
  void addMessage(Message message);

  /**
   * Returns all of the Messages in the conversation with the parameter conversationId, or an empty
   * List if the conversation isn't found.
   */
  public List<Message> getMessagesInConversation(UUID conversationId);
}
