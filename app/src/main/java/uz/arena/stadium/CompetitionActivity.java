package uz.arena.stadium;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import uz.arena.stadium.language.Appcompat;

public class CompetitionActivity extends Appcompat {
    BottomNavigationView bottomNavigationView;
    FloatingActionButton actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);

        actionButton = findViewById(R.id.floating2);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomappbar2);
        bottomNavigationView.setSelectedItemId(R.id.mPerson);

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
                        startActivity(new Intent(getApplicationContext()
                                , SearchActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.mPerson:
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