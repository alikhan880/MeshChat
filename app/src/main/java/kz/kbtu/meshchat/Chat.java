package kz.kbtu.meshchat;

/**
 * Created by aqali on 7/5/17 for MeshChat.
 */

public class Chat {
	private User user1, user2;
	private String lastMessage;
	
	public Chat(User user1, User user2, String lastMessage) {
		this.user1 = user1;
		this.user2 = user2;
		this.lastMessage = lastMessage;
	}
	
	public Chat(User user1, User user2) {
		this.user1 = user1;
		this.user2 = user2;
	}
	
	public String getHash() {
		String a = user1.getHash();
		String b = user2.getHash();
		if (a.compareTo(b) == -1) {
			String t = a;
			a = b;
			b = t;
		}
		return Utils.hash(a + b);
	}
	
	public User getUser1() {
		return user1;
	}
	
	public void setUser1(User user1) {
		this.user1 = user1;
	}
	
	public User getUser2() {
		return user2;
	}
	
	public void setUser2(User user2) {
		this.user2 = user2;
	}
	
	public String getLastMessage() {
		return lastMessage;
	}
	
	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}
	
	public static String getChatHash(String a, String b) {
		if (a.compareTo(b) > 0) {
			final String t = a;
			a = b;
			b = t;
		}
		return Utils.hash(a + b);
	}
}
