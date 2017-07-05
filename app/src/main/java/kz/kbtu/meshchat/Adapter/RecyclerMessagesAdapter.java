package kz.kbtu.meshchat.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import kz.kbtu.meshchat.Chat;
import kz.kbtu.meshchat.Message;
import kz.kbtu.meshchat.R;
import kz.kbtu.meshchat.Utils;

/**
 * Created by aqali on 7/5/17 for MeshChat.
 */

public class RecyclerMessagesAdapter extends RecyclerView.Adapter<RecyclerMessagesAdapter.ViewHolder> {
	
	private ArrayList<Message> messages;
	private Chat chat;
	
	public RecyclerMessagesAdapter(ArrayList<Message> messages, Chat chat) {
		this.messages = messages;
		this.chat = chat;
	}
	
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_message, parent);
		return new ViewHolder(v);
	}
	
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Message msg = messages.get(position);
		String curUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
		String curUserHash = Utils.hash(curUserEmail);
		holder.rightSide.setVisibility(View.GONE);
		holder.leftSide.setVisibility(View.VISIBLE);
		if (msg.getMessageUser().getHash().equals(curUserHash)) {
			holder.leftSide.setVisibility(View.GONE);
			holder.rightSide.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public int getItemCount() {
		return 0;
	}
	
	public class ViewHolder extends RecyclerView.ViewHolder {
		private RelativeLayout leftSide, rightSide;
		private TextView message1TextView, message2TextView;
		public ViewHolder(View itemView) {
			super(itemView);
		}
	}
}
