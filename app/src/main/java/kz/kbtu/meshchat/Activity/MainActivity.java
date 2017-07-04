package kz.kbtu.meshchat.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import kz.kbtu.meshchat.Fragment.FriendsFragment;
import kz.kbtu.meshchat.Fragment.MessagesFragment;
import kz.kbtu.meshchat.Fragment.RecentFragment;
import kz.kbtu.meshchat.R;
import kz.kbtu.meshchat.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int SIGN_IN_REQUEST_CODE = 404;
    private static final String TAG = "DEBUG";
    private FirebaseAuth mAuth;
    private TextView tvUsername;
    private TextView tvUserEmail;
    private ImageView ivUserPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        bind();
        authorize();
    }


    private void loadProfile(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri uri = user.getPhotoUrl();

        tvUsername.setText(name);
        tvUserEmail.setText(email);
        Picasso.with(MainActivity.this).load(uri).into(ivUserPhoto);
    }

    private void bind(){
        NavigationView navView = (NavigationView)findViewById(R.id.nav_view);
        View v = navView.getHeaderView(0);
        tvUsername = (TextView)v.findViewById(R.id.textView_username);
        tvUserEmail = (TextView)v.findViewById(R.id.textView_user_email);
        ivUserPhoto = (ImageView)v.findViewById(R.id.image_user);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case SIGN_IN_REQUEST_CODE:
                if(resultCode == RESULT_OK){
                    String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    db.getReference("/users").push().setValue(new User(name, email));
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                    loadProfile();
                    showFragment(new RecentFragment());
                }
                else{
                    finish();
                }
                break;
        }
    }

    private void authorize(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            loadProfile();
            showFragment(new RecentFragment());
        }
        else{
            Intent intent = AuthUI.getInstance().createSignInIntentBuilder().build();
            startActivityForResult(intent, SIGN_IN_REQUEST_CODE);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_recent:
                showFragment(new RecentFragment());
                break;
            case R.id.nav_friends:
                showFragment(new FriendsFragment());
                break;
            case R.id.nav_messages:
                showFragment(new MessagesFragment());
                break;
            case R.id.nav_logout:
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                FirebaseAuth.getInstance().signOut();
                finish();
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(Object object){
        Fragment fragment = (Fragment)object;
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_content, fragment);
        transaction.commit();
    }
}
