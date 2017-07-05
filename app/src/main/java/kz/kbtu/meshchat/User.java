package kz.kbtu.meshchat;

/**
<<<<<<< HEAD
 * Created by abakh on 04-Jul-17.
 */

public class User {
	private String username;
	private String email;

	public User(){

	}

	public User(String username, String email) {
		this.username = username;
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "User{" +
				"username='" + username + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
