package codeu.model.store.basic;

import java.util.ArrayList;
import java.util.List;

import codeu.model.data.Conversation;
import codeu.model.store.interfaces.ConversationStore;

/**
 * Basic implementation of ConversationStore that uses in-memory data structures
 * to hold values. It's a singleton so different servlet classes can access the 
 * same instance. <br/>
 * Because this class uses in-memory data structures, all data is cleared whenever
 * the server is restarted, redeployed, or put in standby mode.
 */
public class BasicConversationStore implements ConversationStore{

	private static BasicConversationStore instance = new BasicConversationStore();
	
	public static BasicConversationStore getInstance() {
		return instance;
	}
	
	private List<Conversation> conversations;

	/**
	 * This class is a singleton, so its constructor is private.
	 * Call getInstance() instead.
	 */
	private BasicConversationStore(){
		conversations = new ArrayList<>();
		conversations.addAll(BasicDefaultDataStore.getInstance().getDefaultConversations());
	}

	@Override
	public List<Conversation> getAllConversations() {
		return conversations;
	}

	@Override
	public void addConversation(Conversation conversation) {
		conversations.add(conversation);
	}

	@Override
	public boolean isTitleTaken(String title) {
		for(Conversation conversation : conversations){
			if(conversation.getTitle().equals(title)){
				return true;
			}
		}
		return false;
	}

	@Override
	public Conversation getConversationWithTitle(String title) {
		for(Conversation conversation : conversations){
			if(conversation.getTitle().equals(title)){
				return conversation;
			}
		}
		return null;
	}
}
