package kz.kbtu.meshchat;

import java.util.Random;

/**
 * Created by abakh on 11-Jul-17.
 */

public class Notification {
    private String name;
    private String text;
    private String hashUserTo;
    private int randNumb;


    public Notification(String name, String text, String hashUserTo) {
        this.name = name;
        this.text = text;
        this.hashUserTo = hashUserTo;
        this.randNumb = new Random().nextInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHashUserTo() {
        return hashUserTo;
    }

    public void setHashUserTo(String hashUserTo) {
        this.hashUserTo = hashUserTo;
    }

    public int getRandNumb() {
        return randNumb;
    }

    public void setRandNumb(int randNumb) {
        this.randNumb = randNumb;
    }
}
