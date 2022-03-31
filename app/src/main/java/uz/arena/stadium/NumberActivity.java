package uz.arena.stadium;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.santalu.maskedittext.MaskEditText;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import uz.arena.stadium.language.Appcompat;

public class NumberActivity extends Appcompat {
    ImageView back;
    Button btn_number;
    MaskEditText edit_number;
    TextView txt_oddiy;
    private FirebaseAuth auth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack;
    AppCompatButton inButton;
    LinearLayout line_prog;
    GoogleSignInClient googleSignInClient;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_number);

        auth = FirebaseAuth.getInstance();


        FirebaseUser firebaseUser = auth.getCurrentUser();

        if (firebaseUser != null) {
            startActivity(new Intent(NumberActivity.this, HomeActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }

        back = findViewById(R.id.img_back);
        btn_number = findViewById(R.id.number_btn);
        edit_number = findViewById(R.id.edit_number);
        txt_oddiy = findViewById(R.id.txt_oddiy);
        inButton = findViewById(R.id.sign_btn);
        line_prog = findViewById(R.id.line_prog);

        btn_number.setOnClickListener(v -> {
            btn_number.setVisibility(View.GONE);
            line_prog.setVisibility(View.VISIBLE);
            String country_code = Objects.requireNonNull(edit_number.getText()).toString();
            if (!country_code.isEmpty()) {
                PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(country_code)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(NumberActivity.this)
                        .setCallbacks(mCallBack)
                        .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
            } else {
                txt_oddiy.setText(R.string.sms_kod);
                txt_oddiy.setTextColor(Color.RED);
                txt_oddiy.setVisibility(View.VISIBLE);
            }
        });

        mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                txt_oddiy.setText(e.getMessage());
                txt_oddiy.setTextColor(Color.RED);
                txt_oddiy.setVisibility(View.VISIBLE);
                btn_number.setVisibility(View.VISIBLE);
                line_prog.setVisibility(View.GONE);
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verificationId, forceResendingToken);
                txt_oddiy.setText(R.string.sms_yubormoqdamiz);
                txt_oddiy.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent otpIntent = new Intent(NumberActivity.this, SmsActivity.class);
                        otpIntent.putExtra("mobile", (Objects.requireNonNull(edit_number.getText())).toString());
                        otpIntent.putExtra("verificationId", verificationId);
                        startActivity(otpIntent);
                        otpIntent.putExtra("auth", verificationId);
                        startActivity(otpIntent);
                    }
                }, 500);
            }
        };

        back.setOnClickListener(v -> {
            Intent intent = new Intent(NumberActivity.this, LanguageActivity.class);
            startActivity(intent);
            finish();
        });

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN
        ).requestIdToken("10001357942-tino2tpohj6pp7f7ddbm7mbvu1gca7ve.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(NumberActivity.this,
                googleSignInOptions);

        inButton.setOnClickListener(v -> {
            Intent intent = googleSignInClient.getSignInIntent();
            startActivityForResult(intent, 100);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            if (signInAccountTask.isSuccessful()) {

                String s = "Google bilan yaxshi";
                displayToast(s);

                try {
                    GoogleSignInAccount googleSignInAccount = signInAccountTask
                            .getResult(ApiException.class);
                    if (googleSignInAccount != null) {
                        AuthCredential authCredential = GoogleAuthProvider
                                .getCredential(googleSignInAccount.getIdToken()
                                        , null);
                        auth.signInWithCredential(authCredential)
                                .addOnCompleteListener(this, task -> {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(NumberActivity.this, HomeActivity.class)
                                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                        finish();
                                        displayToast("Hal bo'lidimi bilmadim");
                                    } else {
                                        displayToast("Hal bo'lmadi aniq( : " + Objects.requireNonNull(task.getException())
                                                .getMessage());
                                    }
                                });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser mUser = auth.getCurrentUser();
        if (mUser != null) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }
    }

    private void signIn(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                sendToMain();
            } else {
                txt_oddiy.setText(Objects.requireNonNull(task.getException()).getMessage());
                txt_oddiy.setTextColor(Color.RED);
                txt_oddiy.setVisibility(View.VISIBLE);
            }
            line_prog.setVisibility(View.GONE);
            btn_number.setVisibility(View.VISIBLE);
        });
    }

    private void sendToMain() {
        Intent mainIntent = new Intent(NumberActivity.this, HomeActivity.class);
        startActivity(mainIntent);
        finish();
    }


}