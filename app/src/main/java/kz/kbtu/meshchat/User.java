package kz.kbtu.meshchat;

/**
 * Created by abakh on 04-Jul-17.
 */

public class User {
    private String username;
    private String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
