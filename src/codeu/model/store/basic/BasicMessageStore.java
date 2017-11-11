package codeu.model.store.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import codeu.model.data.Message;
import codeu.model.store.interfaces.MessageStore;

/**
 * Basic implementation of MessageStore that uses in-memory data structures
 * to hold values. It's a singleton so different servlet classes can access the 
 * same instance. <br/>
 * Because this class uses in-memory data structures, all data is cleared whenever
 * the server is restarted, redeployed, or put in standby mode.
 */
public class BasicMessageStore implements MessageStore{

	private static BasicMessageStore instance = new BasicMessageStore();

	public static BasicMessageStore getInstance() {
		return instance;
	}

	private List<Message> messages;

	/**
	 * This class is a singleton, so its constructor is private.
	 * Call getInstance() instead.
	 */
	private BasicMessageStore(){
		messages = new ArrayList<>();
		messages.addAll(BasicDefaultDataStore.getInstance().getDefaultMessages());
	}

	@Override
	public void addMessage(Message message) {
		messages.add(message);
	}
	
	@Override
	public List<Message> getMessagesInConversation(UUID conversationId) {
		
		List<Message> messagesInConversation = new ArrayList<>();
		
		for(Message message : messages){
			if(message.getConversation().equals(conversationId)){
				messagesInConversation.add(message);
			}
		}
		
		return messagesInConversation;
	}
}
