package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

public class Conversation {
  public final UUID id;
  public final UUID owner;
  public final Instant creation;
  public final String title;

  public Conversation(UUID id, UUID owner, String title, Instant creation) {
    this.id = id;
    this.owner = owner;
    this.creation = creation;
    this.title = title;
  }

  public UUID getId() {
    return id;
  }

  public UUID getOwnerId() {
    return owner;
  }

  public String getTitle() {
    return title;
  }

  public Instant getCreationTime() {
    return creation;
  }
}
