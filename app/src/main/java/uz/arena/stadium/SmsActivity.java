package uz.arena.stadium;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.gne.www.lib.PinView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;
import uz.arena.stadium.language.Appcompat;

public class SmsActivity extends Appcompat {
    private String OTP;
    private FirebaseAuth auth;
    private DatabaseReference RootRef;
    private PinView smsCodeView;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        auth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
        currentUser = auth.getCurrentUser();
        Button mVerifyCodeBtn = findViewById(R.id.sms_btn);
        smsCodeView = findViewById(R.id.pinview);
        TextView texMobile = findViewById(R.id.number_txt);
        texMobile.setText(
                getIntent().getStringExtra("mobile")
        );

        OTP = getIntent().getStringExtra("auth");
        mVerifyCodeBtn.setOnClickListener(v -> {
            String verification_code = smsCodeView.getText();
            if (!verification_code.isEmpty()) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(OTP, verification_code);
                signIn(credential);
            } else {
                Toasty.info(SmsActivity.this, R.string.kod_kiriting, Toasty.LENGTH_SHORT, true).show();
            }
        });
    }

    private void signIn(PhoneAuthCredential credential)
    {
        auth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                sendToMain();
            } else {
                Toasty.error(SmsActivity.this, (R.string.hato_kiritildi), Toasty.LENGTH_SHORT, true).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            sendToMain();
            finish();
        }
    }

    private void sendToMain()
    {
        Intent intent = new Intent(SmsActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}