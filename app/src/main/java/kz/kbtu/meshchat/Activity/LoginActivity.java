package kz.kbtu.meshchat.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kz.kbtu.meshchat.R;

public class LoginActivity extends AppCompatActivity {

    private static final int SIGN_IN_REQUEST_CODE = 404;
	private static final int MAIN_ACTIVITY_REQUEST_CODE = 202;
	private static final int REGISTER_ACTIVITY_REQUEST_CODE = 203;
	private static final String TAG = "LoginActivity";
	
	private TextInputEditText emailEditText;
	private TextInputEditText passwordEditText;
	private Button loginButton;
	private TextView signUpTextView;
	private FirebaseAuth mAuth;
	private FirebaseAuth.AuthStateListener mAuthStateListener;
	private ProgressDialog dialog;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
		emailEditText = (TextInputEditText) findViewById(R.id.emailEditText);
		passwordEditText = (TextInputEditText) findViewById(R.id.passwordEditText);
		
		emailEditText.setText("aisultan.kali@gmail.com");
		passwordEditText.setText("Qwerty123");
		
		loginButton = (Button) findViewById(R.id.loginButton);
		signUpTextView = (TextView) findViewById(R.id.signUpTextView);
		mAuth = FirebaseAuth.getInstance();
		mAuthStateListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				
			}
		};
		mAuth.addAuthStateListener(mAuthStateListener);
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				authorize();
			}
		});
		
		signUpTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivityForResult(intent, REGISTER_ACTIVITY_REQUEST_CODE);
			}
		});
    }
	
	@Override
	protected void onStop() {
		super.onStop();
		if (mAuthStateListener != null)
			mAuth.removeAuthStateListener(mAuthStateListener);
	}
	
	private void authorize() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            String email = emailEditText.getText().toString();
	        String password = passwordEditText.getText().toString();
	        dialog = new ProgressDialog(this);
	        dialog.setMessage("Wait a moment");
	        dialog.show();
	        mAuth.signInWithEmailAndPassword(email, password)
			        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
				        @Override
				        public void onComplete(@NonNull Task<AuthResult> task) {
					        if (dialog.isShowing())
					        	dialog.dismiss();
					        if (!task.isSuccessful()) {
						        Log.w(TAG, "signInWithEmail:failed", task.getException());
						        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
					        } else {
					            setResult(RESULT_OK);
						        finish();
					        }
				        }
			        });
//	          Intent intent = AuthUI.getInstance().createSignInIntentBuilder().build();
//            startActivityForResult(intent, SIGN_IN_REQUEST_CODE);
        } else {
            setResult(RESULT_OK);
	        finish();
        }
    }
	
	@Override
	public void onBackPressed() {
		setResult(RESULT_CANCELED);
		super.onBackPressed();
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case MAIN_ACTIVITY_REQUEST_CODE:
                if(resultCode == RESULT_OK){
                    authorize();
                }
                break;
	        case REGISTER_ACTIVITY_REQUEST_CODE:
	        	if (resultCode == RESULT_OK) {
			        setResult(RESULT_OK);
			        finish();
		        }
		        break;
        }
    }
}
