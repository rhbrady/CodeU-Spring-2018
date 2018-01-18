package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

public class Message {

  private final UUID id;
  private final UUID conversation;
  private final UUID author;
  private final String content;
  private final Instant creation;

  public Message(UUID id, UUID conversation, UUID author, String content, Instant creation) {
    this.id = id;
    this.conversation = conversation;
    this.author = author;
    this.content = content;
    this.creation = creation;
  }

  public UUID getId() {
    return id;
  }

  public UUID getConversationId() {
    return conversation;
  }

  public UUID getAuthorId() {
    return author;
  }

  public String getContent() {
    return content;
  }

  public Instant getCreationTime() {
    return creation;
  }
}
