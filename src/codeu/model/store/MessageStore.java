package codeu.model.store;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import codeu.model.data.Message;

/**
 * This class is responsible for maintaining Message data.
 * It's a singleton so different servlet classes can access the same instance.
 */
public class MessageStore {

	private static MessageStore instance = new MessageStore();

	public static MessageStore getInstance() {
		return instance;
	}

	private List<Message> messages;

	/**
	 * This class is a singleton, so its constructor is private.
	 * Call getInstance() instead.
	 */
	private MessageStore(){
		messages = new ArrayList<>();
		messages.addAll(DefaultDataStore.getInstance().getMessages());
	}

	public void addMessage(Message message) {
		messages.add(message);
	}

	/**
	 * Returns all of the Messages in the conversation with the parameter conversationId,
	 * or an empty list if the conversation isn't found.
	 */
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
