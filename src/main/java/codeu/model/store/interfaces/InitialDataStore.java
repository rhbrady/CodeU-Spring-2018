package codeu.model.store.interfaces;

import java.util.List;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.interfaces.InitialDataStoreException;

/**
 * This interface defines the minimum set of functions that must be implemented
 * to create the initial state of the system.<br/>
 * Note that the data must be consistent, i.e. if a Message has an author,
 * that author must be found in the Users list.
 */
public interface InitialDataStore {
  public boolean isValid();
  public List<User> getAllUsers() throws InitialDataStoreException;
  public List<Conversation> getAllConversations() throws InitialDataStoreException;
  public List<Message> getAllMessages() throws InitialDataStoreException;
}
