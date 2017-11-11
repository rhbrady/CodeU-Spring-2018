package codeu.model.store.interfaces;

import java.util.UUID;

import codeu.model.data.User;

/**
 * This interface defines the minimum set of functions that must be implemented
 * to create a store of User objects.
 */
public interface UserStore {

	/**
	 * Returns the User with the parameter username, or null if the User is not found.
	 */
	public User getUser(String username);

	/**
	 * Returns the User with the parameter id, or null if the User is not found.
	 */
	public User getUser(UUID id);

	/**
	 * Adds a User to the store.
	 */
	public void addUser(User user);

	/**
	 * Returns true if a User already has the parameter username.
	 */
	public boolean isUserRegistered(String username);

}
