package codeu.model.data;

import java.util.UUID;

public class Message {

	private final UUID id;
	private final UUID conversation;
	private final UUID author;
	private final String content;
	private final long creation;

	public Message(UUID id, UUID conversation, UUID author, String content, long creation) {
		this.id = id;
		this.conversation = conversation;
		this.author = author;
		this.content = content;
		this.creation = creation;
	}
	
	public UUID getConversation(){
		return conversation;
	}
	
	public UUID getAuthor(){
		return author;
	}
	
	public String getContent(){
		return content;
	}
}