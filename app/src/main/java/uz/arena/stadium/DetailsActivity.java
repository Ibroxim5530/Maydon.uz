package uz.arena.stadium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    Uri uri;
    DatabaseReference reference;
    private FirebaseAuth mAuth;
    ImageView back, img_card;
    TextView name, bathroom, dimensions, start_off, end_off, start_on, end_on, summa;
    LinearLayout line_1_off, line_1_on;
    ProgressBar progressBar;
    Button btn_det;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        name = findViewById(R.id.arena_name_det);
        bathroom = findViewById(R.id.check1_det);
        btn_det = findViewById(R.id.det_btn);
        dimensions = findViewById(R.id.dimensions_det);
        summa = findViewById(R.id.arena_summa_det);
        start_off = findViewById(R.id.start_time_off);
        end_off = findViewById(R.id.end_time_off);
        start_on = findViewById(R.id.start_time_on);
        end_on = findViewById(R.id.end_time_on);
        line_1_off = findViewById(R.id.line_time1_off);
        line_1_on = findViewById(R.id.line_time1_on);
        back = findViewById(R.id.det_back);
        img_card = findViewById(R.id.img_card_det);
        progressBar = findViewById(R.id.progress_det_card);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        line_1_off.setOnClickListener(new View.OnClickListener() {
            @Override           
            public void onClick(View v) {
                line_1_off.setVisibility(View.GONE);
                line_1_on.setVisibility(View.VISIBLE);
            }
        });

        name.setText(getIntent().getStringExtra("arena_name"));
        String have_bathroom = getIntent().getStringExtra("check_1");
        if(have_bathroom.equals("1")){
            bathroom.setText("Bathroom");
        }else{
            bathroom.setText("Yo'q");
        }
        dimensions.setText(getIntent().getStringExtra("dimensions"));
        start_off.setText(getIntent().getStringExtra("start_time"));
        end_off.setText(getIntent().getStringExtra("start_end"));
        start_on.setText(getIntent().getStringExtra("start_end"));
        end_on.setText(getIntent().getStringExtra("start_end"));
        summa.setText(getIntent().getStringExtra("summa"));

        Bundle extras1 = getIntent().getExtras();
        uri = Uri.parse(extras1.getString("rasim1"));

        Picasso.get().load(uri).into(img_card, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
                img_card.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}