package kz.kbtu.meshchat.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import kz.kbtu.meshchat.Adapter.RecyclerMessagesAdapter;
import kz.kbtu.meshchat.Chat;
import kz.kbtu.meshchat.Message;
import kz.kbtu.meshchat.R;
import kz.kbtu.meshchat.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {

	private RecyclerView recyclerView;
	private ArrayList<Message> messageArrayList;

    public MessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
	    
	    View v = inflater.inflate(R.layout.fragment_messages, container, false);
	    
	    User user1 = new User("Abacaba1", "abacaba1@gmail.com");
	    User user2 = new User("Abacaba2", "abacaba2@gmail.com");
	    
	    recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
	    messageArrayList = new ArrayList<>();
	    messageArrayList.add(new Message(user1, "Hi, there!"));
	    messageArrayList.add(new Message(user2, "Hello"));
	    messageArrayList.add(new Message(user1, "Mirinda"));
	    messageArrayList.add(new Message(user1, "Aubacabu"));
	    
	    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
	    Chat chat = new Chat(user1, user2);
	    recyclerView.setAdapter(new RecyclerMessagesAdapter(messageArrayList, chat));
	    
        return v;
    }

}
