package uz.arena.stadium;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import uz.arena.stadium.fragment_top_menu.HomeFragment;
import uz.arena.stadium.language.Appcompat;

public class HomeActivity extends Appcompat {
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    TextView name, mail;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton actionButton;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();
        actionButton = findViewById(R.id.floating);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomappbar);
        bottomNavigationView.setSelectedItemId(R.id.mHome);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, new HomeFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.mHome:
                        return true;

                    case R.id.mSearch:
                        startActivity(new Intent(getApplicationContext()
                                , SearchActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.mPerson:
                        startActivity(new Intent(getApplicationContext()
                                , CompetitionActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.mSetting:
                        startActivity(new Intent(getApplicationContext()
                                , MoreActivity.class));
                        overridePendingTransition(0, 0);
                        return false;
                }
                return false;

            }
        });

        actionButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, AddActivity.class);
            overridePendingTransition(0, 0);
            startActivity(intent);
        });

//        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
//        if (signInAccount != null) {
//            name.setText(signInAccount.getDisplayName());
//            mail.setText(signInAccount.getEmail());
//        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (currentUser != null) {
            progressDialog = new ProgressDialog(HomeActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_item);
            progressDialog.getWindow().setBackgroundDrawableResource(
                    android.R.color.transparent);
            VerifyUserExistance();

            ConnectivityManager connectivityManager = (ConnectivityManager)
                    getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.internet_layout);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations =
                        android.R.style.Animation_Dialog;
                Button button = dialog.findViewById(R.id.btn_internet);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recreate();
                    }
                });
                dialog.show();
            }

        } else {
            SendUserToLoginActivity();
        }
    }


    private void VerifyUserExistance() {
        String currentUserID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        RootRef.child("User").child(currentUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("name").exists()) {
                            if (progressDialog.isShowing()) progressDialog.dismiss();

                        } else {
                            SendUserToSettingsActivityy();
                            progressDialog.dismiss();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void SendUserToSettingsActivityy() {
        Intent settingsIntent = new Intent(HomeActivity.this, InfoActivity.class);
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(settingsIntent);
        finish();
    }

    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(HomeActivity.this, NumberActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }


}