package uz.arena.stadium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SearchActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FloatingActionButton actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        actionButton = findViewById(R.id.floating3);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomappbar3);
        bottomNavigationView.setSelectedItemId(R.id.mSearch);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.mHome:
                        startActivity(new Intent(getApplicationContext()
                                ,HomeActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.mSearch:
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
    }
}