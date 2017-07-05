package kz.kbtu.meshchat.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import kz.kbtu.meshchat.Interface.RecyclerItemListener;
import kz.kbtu.meshchat.R;
import kz.kbtu.meshchat.User;

/**
 * Created by abakh on 05-Jul-17.
 */

public class RecyclerFriendsAdapter extends RecyclerView.Adapter<RecyclerFriendsAdapter.RecyclerFriendsViewHolder> {
    private ArrayList<User> users;
    private RecyclerItemListener listener;

    public RecyclerFriendsAdapter(ArrayList<User> users, RecyclerItemListener listener) {
        this.users = users;
        this.listener = listener;
    }

    @Override
    public RecyclerFriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_friends, parent, false);
        return new RecyclerFriendsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerFriendsViewHolder holder, int position) {
        User user = users.get(position);
        holder.username.setText(user.getUsername());
        holder.email.setText(user.getEmail());

    }

    public User getItem(int position){
        return users.get(position);
    }
    @Override
    public int getItemCount() {
        return users.size();
    }

    public class RecyclerFriendsViewHolder extends RecyclerView.ViewHolder {
        private TextView username;
        private TextView email;

        public RecyclerFriendsViewHolder(View itemView) {
            super(itemView);
            username = (TextView)itemView.findViewById(R.id.recycler_item_friends_textView_username);
            email = (TextView)itemView.findViewById(R.id.recycler_item_friends_textView_email);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.itemClicked(getAdapterPosition());
                }
            });

        }

    }
}
