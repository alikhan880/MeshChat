package kz.kbtu.meshchat.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kz.kbtu.meshchat.R;

public class LoginActivity extends AppCompatActivity {

    private static final int SIGN_IN_REQUEST_CODE = 404;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        authorize();
    }

    private void authorize() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            Intent intent = AuthUI.getInstance().createSignInIntentBuilder().build();
            startActivityForResult(intent, SIGN_IN_REQUEST_CODE);
        }
        else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, 202);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 202:
                if(resultCode == RESULT_OK){
                    authorize();
                }
                break;
        }
    }
}
