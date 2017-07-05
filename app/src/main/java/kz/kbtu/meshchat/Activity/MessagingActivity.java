package kz.kbtu.meshchat.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import kz.kbtu.meshchat.Adapter.RecyclerMessagesAdapter;
import kz.kbtu.meshchat.Chat;
import kz.kbtu.meshchat.Message;
import kz.kbtu.meshchat.R;
import kz.kbtu.meshchat.User;

public class MessagingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Message> messageArrayList;
    private RecyclerMessagesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        User user1 = new User("Abacaba1", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        User user2 = new User("Abacaba2", "abacaba2@gmail.com");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_messaging);
        messageArrayList = new ArrayList<>();
        messageArrayList.add(new Message(user1, "Hi, there!"));
        messageArrayList.add(new Message(user2, "Hello"));
        messageArrayList.add(new Message(user1, "Mirinda"));
        messageArrayList.add(new Message(user1, "Aubacabu"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Chat chat = new Chat(user1, user2);
        adapter = new RecyclerMessagesAdapter(messageArrayList, chat);
        recyclerView.setAdapter(adapter);
		
	}
}
