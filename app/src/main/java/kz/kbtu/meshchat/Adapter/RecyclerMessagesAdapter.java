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
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_message, parent, false);
		return new ViewHolder(v);
	}
	
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Message msg = messages.get(position);
		String curUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
		String curUserHash = Utils.hash(curUserEmail);
		if (msg.getMessageUser().getHash().equals(curUserHash)) {
			holder.leftSide.setVisibility(View.GONE);
			holder.rightSide.setVisibility(View.VISIBLE);
			holder.message2TextView.setText(msg.getMessageText());
		} else{
			holder.rightSide.setVisibility(View.GONE);
			holder.leftSide.setVisibility(View.VISIBLE);
			holder.message1TextView.setText(msg.getMessageText());
		}
	}
	
	@Override
	public int getItemCount() {
		return messages.size();
	}
	
	public class ViewHolder extends RecyclerView.ViewHolder {
		private RelativeLayout leftSide, rightSide;
		private TextView message1TextView, message2TextView;
		public ViewHolder(View itemView) {
			super(itemView);
			leftSide = (RelativeLayout) itemView.findViewById(R.id.leftSideRelativeLayout);
			rightSide = (RelativeLayout) itemView.findViewById(R.id.rightSideRelativeLayout);
			message1TextView = (TextView) itemView.findViewById(R.id.message1TextView);
			message2TextView = (TextView) itemView.findViewById(R.id.message2TextView);
		}
	}
}
