package kz.kbtu.meshchat.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kz.kbtu.meshchat.Adapter.RecyclerMessagesAdapter;
import kz.kbtu.meshchat.Chat;
import kz.kbtu.meshchat.Message;
import kz.kbtu.meshchat.R;
import kz.kbtu.meshchat.User;
import kz.kbtu.meshchat.Utils;

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
    private User userFrom;
	private Chat chat;
    private String chatHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        bind();
        Intent intent = getIntent();
        userTo = intent.getParcelableExtra("user");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_messaging);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageArrayList = new ArrayList<>();
        go();

	}


	private void go(){
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                //Take current user from firebase as User class;
                DatabaseReference refForUser = FirebaseDatabase.getInstance().getReference().child("users")
                        .child(Utils.hash(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                refForUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        userFrom = dataSnapshot.getValue(User.class);
//                        Log.d("DEBUG", userFrom.toString());
                        chatHash = Chat.getChatHash(userTo.getHash(),
                                userFrom.getHash());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                ////


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter = new RecyclerMessagesAdapter(messageArrayList, chat);
                recyclerView.setAdapter(adapter);
//                Log.d("HASH", chatHash);
            }
        };
        task.execute();
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
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/messages");

        ref.child(chatHash).push().setValue(new Message(userFrom, text));

	    
//        HERE WE NEED TO FINISH SENDING TO FIREBASE. COULDN'T UNDERSTAND THE WAY HOW YOU WANT TO GET SENDING USER INSTANCE
    }
}
