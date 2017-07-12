package kz.kbtu.meshchat.Adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
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
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_message_sender, parent, false);
		return new ViewHolder(v);
	}
	
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Message msg = messages.get(position);
		String curUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
		String curUserHash = Utils.hash(curUserEmail);
		if (!msg.getMessageUser().getHash().equals(curUserHash)) {
			holder.rootLinearLayout.setGravity(Gravity.LEFT);
			holder.cardView.setCardBackgroundColor(Color.rgb(0xce, 0xce, 0xce));
		} else {
			holder.rootLinearLayout.setGravity(Gravity.RIGHT);
			holder.cardView.setCardBackgroundColor(Color.rgb(0xa6,0xfc,0x99));
		}
		holder.dateTextView.setText(android.text.format.DateFormat.format("hh:mm", msg.getMessageTime()));
		holder.authorTextView.setText(msg.getMessageUser().getUsername());
		holder.contentTextView.setText(msg.getMessageText());
	}
	
	@Override
	public int getItemCount() {
		return messages.size();
	}
	
	public class ViewHolder extends RecyclerView.ViewHolder {
		private LinearLayout rootLinearLayout;
		private CardView cardView;
		private TextView authorTextView, contentTextView, dateTextView;
		public ViewHolder(View itemView) {
			super(itemView);
			cardView = (CardView) itemView.findViewById(R.id.cardView);
			rootLinearLayout = (LinearLayout) itemView.findViewById(R.id.rootLinearLayout);
			authorTextView = (TextView) itemView.findViewById(R.id.authorTextView);
			contentTextView = (TextView) itemView.findViewById(R.id.contentTextView);
			dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
		}
	}
}
