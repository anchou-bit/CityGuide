package com.example.cityguide;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.cityguide.ui.home.HomeFragment;
import com.example.cityguide.ui.map.MapFragment;
import com.example.cityguide.ui.favorites.FavoritesFragment;
import com.example.cityguide.ui.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Устанавливаем начальный фрагмент
        loadFragment(new HomeFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;

                        int itemId = item.getItemId();
                        if (itemId == R.id.navigation_home) {
                            selectedFragment = new HomeFragment();
                        } else if (itemId == R.id.navigation_map) {
                            selectedFragment = new MapFragment();
                        } else if (itemId == R.id.navigation_favorites) {
                            selectedFragment = new FavoritesFragment();
                        } else if (itemId == R.id.navigation_profile) {
                            selectedFragment = new ProfileFragment();
                        }

                        return loadFragment(selectedFragment);
                    }
                });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}