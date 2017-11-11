package codeu.model.store.interfaces;

import java.util.List;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;

/**
 * This interface defines the minimum set of functions that must be implemented
 * to create a store of default data.<br/>
 * Note that the data must be consistent, i.e. if a Message has an author,
 * that author must be found in the Users list.
 */
public interface DefaultDataStore {
	public List<User> getDefaultUsers();
	public List<Conversation> getDefaultConversations();
	public List<Message> getDefaultMessages();
}
