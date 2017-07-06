package kz.kbtu.meshchat.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import kz.kbtu.meshchat.Adapter.RecyclerMessagesAdapter;
import kz.kbtu.meshchat.Message;
import kz.kbtu.meshchat.R;
import kz.kbtu.meshchat.User;

/*

PLEASE CHECK LINE NUMBER 65




 */

public class MessagingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Message> messageArrayList;
    private RecyclerMessagesAdapter adapter;
    private EditText editTextMessage;
    private Button buttonSendMessage;
    private User userTo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        Intent intent = getIntent();
        userTo = intent.getParcelableExtra("user");
        bind();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_messaging);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageArrayList = new ArrayList<>();

        adapter = new RecyclerMessagesAdapter(messageArrayList);
        recyclerView.setAdapter(adapter);
		
	}


	private void bind(){
        editTextMessage = (EditText)findViewById(R.id.edit_text_send);
        buttonSendMessage = (Button)findViewById(R.id.button_send_message);
        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(editTextMessage.getText().toString());
            }
        });
    }


    private void sendMessage(String text){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        String userFromEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String userToEmail = userTo.getEmail();
        //HERE WE NEED TO FINISH SENDING TO FIREBASE. COULDN'T UNDERSTAND THE WAY HOW YOU WANT TO GET SENDING USER INSTANCE
    }
}
