package codeu.model.store.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import codeu.model.data.User;
import codeu.model.store.interfaces.UserStore;

/**
 * Basic implementation of UserStore that uses in-memory data structures
 * to hold values. It's a singleton so different servlet classes can access the 
 * same instance. <br/>
 * Because this class uses in-memory data structures, all data is cleared whenever
 * the server is restarted, redeployed, or put in standby mode.
 */
public class BasicUserStore implements UserStore {

	private static BasicUserStore instance = new BasicUserStore();

	public static BasicUserStore getInstance() {
		return instance;
	}

	private List<User> users;

	/**
	 * This class is a singleton, so its constructor is private.
	 * Call getInstance() instead.
	 */
	private BasicUserStore(){
		users = new ArrayList<>();
		users.addAll(BasicDefaultDataStore.getInstance().getDefaultUsers());
	}

	@Override
	public User getUser(String username) {
		for(User user : users){
			if(user.getName().equals(username)){
				return user;
			}
		}
		return null;
	}
	
	@Override
	public User getUser(UUID id) {
		for(User user : users){
			if(user.getId().equals(id)){
				return user;
			}
		}
		return null;
	}

	@Override
	public void addUser(User user) {
		users.add(user);
	}
	
	@Override
	public boolean isUserRegistered(String username) {
		for(User user : users){
			if(user.getName().equals(username)){
				return true;
			}
		}
		return false;
	}
}
