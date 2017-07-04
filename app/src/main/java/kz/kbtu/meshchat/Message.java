package kz.kbtu.meshchat;

import java.util.Date;

/**
 * Created by abakh on 04-Jul-17.
 */

public class Message {
    private String messageUser;
    private String messageText;
    private long messageTime;


    public Message(String messageUser, String messageText) {
        this.messageUser = messageUser;
        this.messageText = messageText;
        this.messageTime = new Date().getTime();
    }

    public String getMessageUser() {
        return messageUser;
    }

    public String getMessageText() {
        return messageText;
    }

    public long getMessageTime() {
        return messageTime;
    }
}
