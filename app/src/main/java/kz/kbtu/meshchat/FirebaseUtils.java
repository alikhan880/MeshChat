package kz.kbtu.meshchat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by aqali on 7/10/17 for MeshChat.
 */

public class FirebaseUtils {
	public static void getUserByEmailAsync(String email, final User.RetrieveListener listener) {
		String hash = Utils.hash(email);
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(hash);
	
		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				listener.onSuccess(dataSnapshot.getValue(User.class));
			}
			
			@Override
			public void onCancelled(DatabaseError databaseError) {
				listener.onFail();
			}
		});
	}
}
