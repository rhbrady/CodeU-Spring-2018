package codeu.model.data;

import java.util.UUID;

public class Conversation {
	public final UUID id;
	public final UUID owner;
	public final long creation;
	public final String title;

	public Conversation(UUID id, UUID owner, String title, long creation) {
		this.id = id;
		this.owner = owner;
		this.creation = creation;
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public UUID getId() {
		return id;
	}
}