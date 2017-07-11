package kz.kbtu.meshchat.Service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kz.kbtu.meshchat.R;
import kz.kbtu.meshchat.User;
import kz.kbtu.meshchat.Utils;

/**
 * Created by abakh on 11-Jul-17.
 */

public class NotificationService extends Service {
    private String userHash;
    private User user;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    private void listen(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("notification")
                .child(userHash);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                notifyMessage();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                notifyMessage();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private synchronized void notifyMessage(){
        NotificationCompat.Builder builder= new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_message_black).setContentTitle("MeshChat")
                .setContentText("You have new message");
        NotificationManager managerCompat = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        managerCompat.notify(001, builder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show();
        user = intent.getParcelableExtra("user");
        userHash = Utils.hash(user.getEmail());
        listen();
        return START_STICKY;
    }
}
