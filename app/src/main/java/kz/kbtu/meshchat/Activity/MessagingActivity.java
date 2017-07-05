package kz.kbtu.meshchat.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import kz.kbtu.meshchat.Message;
import kz.kbtu.meshchat.R;

public class MessagingActivity extends AppCompatActivity {

    private ArrayList<Message> messages;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
    
		
	}
}
