package codeu.model.store.basic;

import codeu.model.data.User;
import codeu.model.store.agent.PersistentStorageAgent;
import codeu.model.store.basic.BasicDefaultDataStore;
import codeu.model.store.interfaces.UserStore;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Basic implementation of UserStore that uses in-memory data structures to hold values and
 * automatically loads from and saves to PersistentStorageAgent. It's a singleton so different
 * servlet classes can access the same instance.
 */
public class BasicUserStore implements UserStore {

  /**
   * Singleton instance of BasicUserStore.
   */
  private static BasicUserStore instance;

  /**
   * Returns the singleton instance of BasicUserStore that should be shared between all servlet
   * classes. Do not call this function from a test; use getTestInstance() instead.
   */
  public static BasicUserStore getInstance() {
    if(instance == null) {
      instance = new BasicUserStore(PersistentStorageAgent.getInstance());
      instance.loadFromStorage();
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   * @param persistentStorageAgent a mock used for testing
   */
  public static BasicUserStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    if(instance == null) {
      instance = new BasicUserStore(persistentStorageAgent);
    }
    return instance;
  }

  /**
   * The PersistentStorageAgent responsible for loading Users from and saving Users to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /**
   * The in-memory list of Users.
   */
  private List<User> users;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private BasicUserStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    users = new ArrayList<>();
  }

  /**
   * Load Conversation objects from persistent storage.
   * Returns true if the load succeeded. Otherwise users will be empty.
   */
  private boolean loadFromStorage() {
    // If loading from storage, clear existing objects.
    boolean loaded = false;
    users = new ArrayList<>();
    if (persistentStorageAgent.isValid()) {
      try {
        users.addAll(persistentStorageAgent.getAllUsers());
        loaded = true;
      } catch (Exception e) {
        loaded = false;
        System.err.println("ERROR: Unable to load from Datastore (users).");
      }
    }
    return loaded;
  }

  /**
   * Load a set of randomly-generated Message objects.
   * Returns false if a error occurs.
   */
  public boolean loadTestData() {
    boolean loaded = false;
    try {
      users.addAll(BasicDefaultDataStore.getInstance().getAllUsers());
      loaded = true;
    } catch (Exception e) {
      loaded = false;
      System.err.println("ERROR: Unable to establish initial store (users).");
    }
    return loaded;
  }

  /**
   * Access the User object with the given name.
   * Return null if username does not match any existing User.
   */
  @Override
  public User getUser(String username) {
    // This approach will be pretty slow if we have many users.
    for (User user : users) {
      if (user.getName().equals(username)) {
        return user;
      }
    }
    return null;
  }

  /**
   * Access the User object with the given UUID.
   * Return null if the UUID does not match any existing User.
   */
  @Override
  public User getUser(UUID id) {
    for (User user : users) {
      if (user.getId().equals(id)) {
        return user;
      }
    }
    return null;
  }

  /**
   * Add a new user to the current set of users known to the application.
   */
  @Override
  public void addUser(User user) {
    users.add(user);
    persistentStorageAgent.writeThrough(user);
  }

  /**
   * Return true if the given username is known to the application.
   */
  @Override
  public boolean isUserRegistered(String username) {
    for (User user : users) {
      if (user.getName().equals(username)) {
        return true;
      }
    }
    return false;
  }
}
