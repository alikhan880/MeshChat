package kz.kbtu.meshchat;

/**
 * Created by aqali on 7/5/17 for MeshChat.
 */

public class Chat {
	private User user1, user2;
	
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
}
