package codeu.model.store;

import java.util.ArrayList;
import java.util.List;

import codeu.model.data.Conversation;

/**
 * This class is responsible for maintaining Conversation data.
 * It's a singleton so different servlet classes can access the same instance.
 */
public class ConversationStore {

	private static ConversationStore instance = new ConversationStore();
	
	public static ConversationStore getInstance() {
		return instance;
	}
	
	private List<Conversation> conversations;

	/**
	 * This class is a singleton, so its constructor is private.
	 * Call getInstance() instead.
	 */
	private ConversationStore(){
		conversations = new ArrayList<>();
		conversations.addAll(DefaultDataStore.getInstance().getConversations());
	}

	public List<Conversation> getAllConversations() {
		return conversations;
	}

	public void addConversation(Conversation conversation) {
		conversations.add(conversation);
	}

	/**
	 * Returns true if a Conversation already has the parameter title.
	 */
	public boolean isTitleTaken(String title) {
		for(Conversation conversation : conversations){
			if(conversation.getTitle().equals(title)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the Conversation with the parameter title,
	 * or null if no matching Conversation is found.
	 */
	public Conversation getConversationWithTitle(String title) {
		for(Conversation conversation : conversations){
			if(conversation.getTitle().equals(title)){
				return conversation;
			}
		}
		return null;
	}
}
