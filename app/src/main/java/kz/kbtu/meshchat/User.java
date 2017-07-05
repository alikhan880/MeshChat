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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User))
			return false;
		User u = (User) obj;
		return u.getUsername().equals(getUsername()) && u.getEmail().equals(getEmail());
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
