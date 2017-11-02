package codeu.model.store;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import codeu.model.data.User;

/**
 * This class is responsible for maintaining User data.
 * It's a singleton so different servlet classes can access the same instance.
 */
public class UserStore {

	private static UserStore instance = new UserStore();

	public static UserStore getInstance() {
		return instance;
	}

	private List<User> users;

	/**
	 * This class is a singleton, so its constructor is private.
	 * Call getInstance() instead.
	 */
	private UserStore(){
		users = new ArrayList<>();
		users.addAll(DefaultDataStore.getInstance().getUsers());
	}

	/**
	 * Returns the User with the parameter username, or null if the User is not found.
	 */
	public User getUser(String username) {

		for(User user : users){
			if(user.getName().equals(username)){
				return user;
			}
		}

		return null;
	}

	/**
	 * Returns the User with the parameter id, or null if the User is not found.
	 */
	public User getUser(UUID id) {

		for(User user : users){
			if(user.getId().equals(id)){
				return user;
			}
		}

		return null;
	}

	public void addUser(User user) {
		users.add(user);
	}

	/**
	 * Returns true if a User already has the parameter username.
	 */
	public boolean isUserRegistered(String username) {

		for(User user : users){
			if(user.getName().equals(username)){
				return true;
			}
		}

		return false;
	}
}
