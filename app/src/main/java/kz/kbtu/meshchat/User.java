package kz.kbtu.meshchat;

import android.os.Parcel;
import android.os.Parcelable;

/**
<<<<<<< HEAD
 * Created by abakh on 04-Jul-17.
 */

public class User implements Parcelable{
	private String username;
	private String email;

	public User(){

	}

	protected User(Parcel in) {
		username = in.readString();
		email = in.readString();
	}

	public static final Creator<User> CREATOR = new Creator<User>() {
		@Override
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};

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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(username);
		dest.writeString(email);
	}
}
