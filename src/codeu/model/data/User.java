package codeu.model.data;

import java.util.UUID;

public class User {
	public final UUID id;
	public final String name;
	public final long creation;

	public User(UUID id, String name, long creation) {
		this.id = id;
		this.name = name;
		this.creation = creation;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}