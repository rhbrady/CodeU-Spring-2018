package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

public class User {
  private final UUID id;
  private final String name;
  private final Instant creation;

  public User(UUID id, String name, Instant creation) {
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

  public Instant getCreationTime() {
    return creation;
  }
}
