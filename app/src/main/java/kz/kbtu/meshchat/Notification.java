package kz.kbtu.meshchat;

import java.util.Random;

/**
 * Created by abakh on 11-Jul-17.
 */

public class Notification {
    private int number;
    public Notification() {
        this.number = new Random().nextInt();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
