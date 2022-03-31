package uz.arena.stadium;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import uz.arena.stadium.language.Appcompat;
import uz.arena.stadium.language.LanguageManager;

public class LanguageActivity extends Appcompat {
    ImageView btn_next;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        auth = FirebaseAuth.getInstance();
        LinearLayout btn_uz = findViewById(R.id.line_uz);
        LinearLayout btn_ru = findViewById(R.id.line_ru);
        LinearLayout btn_eng = findViewById(R.id.line_eng);
        btn_next = findViewById(R.id.lan_next);

        LanguageManager lang = new LanguageManager(this);

        btn_uz.setOnClickListener(view ->
        {
            lang.updateResource("uz");
            recreate();
        });

        btn_ru.setOnClickListener(view -> {
            lang.updateResource("ru");
            recreate();
        });

        btn_eng.setOnClickListener(view -> {
            lang.updateResource("eng");
            recreate();
        });

        btn_next.setOnClickListener(view -> ElseToMain());
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            sendToMain();
        }
    }

    private void sendToMain() {
        Intent intent = new Intent(LanguageActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void ElseToMain() {
        Intent intent = new Intent(LanguageActivity.this, NumberActivity.class);
        startActivity(intent);
    }
}