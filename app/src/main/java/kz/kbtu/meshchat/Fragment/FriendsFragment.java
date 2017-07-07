package kz.kbtu.meshchat.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import kz.kbtu.meshchat.Activity.MessagingActivity;
import kz.kbtu.meshchat.Adapter.RecyclerFriendsAdapter;
import kz.kbtu.meshchat.Interface.RecyclerItemListener;
import kz.kbtu.meshchat.R;
import kz.kbtu.meshchat.User;


public class FriendsFragment extends Fragment implements RecyclerItemListener {

    private RecyclerView recycler;
    private RecyclerFriendsAdapter adapter;
    private ArrayList<User> users;

    public FriendsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_friends, container, false);
        users = new ArrayList<>();
        getUsers();
        recycler = (RecyclerView)v.findViewById(R.id.recycler_friends);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecyclerFriendsAdapter(users, this);
        recycler.setAdapter(adapter);
        return v;
    }


    private void getUsers(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/users");
        ChildEventListener userListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                Log.d("DEBUG", user.getUsername() + "");
                if(!user.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                    users.add(user);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                Log.d("DEBUG", user.getUsername() + "");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                users.remove(user);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addChildEventListener(userListener);

    }


    @Override
    public void itemClicked(int position) {
        User user = adapter.getItem(position);
//        String chatHash =
//	    Chat chat = ;
//	    FirebaseDatabase.getInstance().getReference("Chats/" + chatHash)
	    Intent intent = new Intent(getActivity(), MessagingActivity.class);
        intent.putExtra("user", user);
//	    intent.putExtra("chat", (new Gson()).toJson(chat));
        startActivity(intent);
    }
}
