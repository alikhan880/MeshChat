package kz.kbtu.meshchat;

import java.util.Date;

/**
 * Created by abakh on 04-Jul-17.
 */

public class Message {
    private User messageUser;
    private String messageText;
    private long messageTime;


    public Message() {
    }

    public Message(User messageUser, String messageText) {
        this.messageUser = messageUser;
        this.messageText = messageText;
        this.messageTime = new Date().getTime();
    }

    public User getMessageUser() {
        return messageUser;
    }

    public String getMessageText() {
        return messageText;
    }

    public long getMessageTime() {
        return messageTime;
    }
}
