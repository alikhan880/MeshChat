package kz.kbtu.meshchat.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import kz.kbtu.meshchat.Adapter.RecyclerMessagesAdapter;
import kz.kbtu.meshchat.Chat;
import kz.kbtu.meshchat.FirebaseUtils;
import kz.kbtu.meshchat.Message;
import kz.kbtu.meshchat.Notification;
import kz.kbtu.meshchat.R;
import kz.kbtu.meshchat.User;
import kz.kbtu.meshchat.Utils;

public class MessagingActivity extends AppCompatActivity {
	private static final String TAG = "MessagingActivity";
	private RecyclerView recyclerView;
    private ArrayList<Message> messageArrayList;
    private RecyclerMessagesAdapter adapter;
    private EditText editTextMessage;
    private Button buttonSendMessage;
    private User userTo;
    private User userFrom;
	private Chat chat;
    private String chatHash;
	
	private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        bind();
        Intent intent = getIntent();
        userTo = intent.getParcelableExtra("user");
	    
        recyclerView = (RecyclerView) findViewById(R.id.recycler_messaging);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
			@Override
			public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
				if(bottom < oldBottom){
					recyclerView.postDelayed(new Runnable() {
						@Override
						public void run() {
							recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
						}
					}, 100);
				}
			}
		});
        messageArrayList = new ArrayList<>();
        go();
	}
	
	private void setListeners() {
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference("messages").child(chatHash);
		ref.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String s) {
				Message message = dataSnapshot.getValue(Message.class);
				messageArrayList.add(message);
				adapter.notifyDataSetChanged();
				recyclerView.scrollToPosition(messageArrayList.size() - 1);
			}
			
			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String s) {
				adapter.notifyDataSetChanged();
			}
			
			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
				adapter.notifyDataSetChanged();
			}
			
			@Override
			public void onChildMoved(DataSnapshot dataSnapshot, String s) {
				adapter.notifyDataSetChanged();
			}
			
			@Override
			public void onCancelled(DatabaseError databaseError) {
				
			}
		});
	}
	
	private void go(){
		dialog = new ProgressDialog(this);
		dialog.setTitle("Wait a second");
		dialog.show();
        FirebaseUtils.getUserByEmailAsync(FirebaseAuth.getInstance().getCurrentUser().getEmail(),
		        new User.RetrieveListener() {
            @Override
            public void onSuccess(User user) {
	            userFrom = user;
	            chatHash = Chat.getChatHash(userFrom.getHash(), userTo.getHash());
	            adapter = new RecyclerMessagesAdapter(messageArrayList, chat);
				setListeners();
	            recyclerView.setAdapter(adapter);
				recyclerView.scrollToPosition(messageArrayList.size() - 1);

	            dialog.dismiss();
            }

            @Override
            public void onFail() {
				Log.d(TAG, "Epic fail");
            }
        });
    }

	private void bind(){
        editTextMessage = (EditText)findViewById(R.id.edit_text_send);
        buttonSendMessage = (Button)findViewById(R.id.button_send_message);
        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTextMessage.getText().toString().equals("")){
					sendMessage(editTextMessage.getText().toString());
					editTextMessage.setText("");
				}
            }
        });
    }


	private void sendMessage(String text){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("messages");
		DatabaseReference refNotify = FirebaseDatabase.getInstance().getReference().child("notifications");
        refNotify.child(Utils.hash(userTo.getEmail()))
				.setValue(new Notification(userFrom.getUsername(), text, Utils.hash(userTo.getEmail())));
		ref.child(chatHash).push().setValue(new Message(userFrom, text));
    }
}
