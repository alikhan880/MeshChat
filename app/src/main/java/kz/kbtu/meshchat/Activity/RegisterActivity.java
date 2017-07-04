package kz.kbtu.meshchat.Activity;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kz.kbtu.meshchat.R;
import kz.kbtu.meshchat.User;

public class RegisterActivity extends AppCompatActivity {
	
	private static final String TAG = "RegisterActivity";
	private FirebaseAuth mAuth;
	private TextInputEditText emailEditText, passwordEditText, handleEditText;
	private Button nextButton;
	private TextView signInTextView;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
	
		emailEditText = (TextInputEditText) findViewById(R.id.emailEditText);
		passwordEditText = (TextInputEditText) findViewById(R.id.passwordEditText);
		handleEditText = (TextInputEditText) findViewById(R.id.handleEditText);
		nextButton = (Button) findViewById(R.id.nextButton);
		signInTextView = (TextView) findViewById(R.id.signInTextView);
		mAuth = FirebaseAuth.getInstance();
		
		signInTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		
		nextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				signUp();
			}
		});
	}
	
	private void signUp() {
		final String handle = handleEditText.getText().toString();
		final String email = emailEditText.getText().toString();
		String password = passwordEditText.getText().toString();
		final User user = new User(handle, email);
		Log.d(TAG, user.toString());
		if (handle.isEmpty()) {
			Toast.makeText(this, "Handle can not be empty", Toast.LENGTH_SHORT).show();
			return;
		}
		dialog = new ProgressDialog(this);
		dialog.setMessage("Wait a moment");
		dialog.show();
		mAuth.createUserWithEmailAndPassword(email, password)
				.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(@NonNull Task<AuthResult> task) {
				if (dialog.isShowing())
					dialog.dismiss();
				if (!task.isSuccessful()) {
					Toast.makeText(RegisterActivity.this, "Failed to sign up", Toast.LENGTH_SHORT).show();
				} else {
					DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").push();
					ref.setValue(user);
					setResult(RESULT_OK);
					finish();
				}
			}
		});
	}
}
