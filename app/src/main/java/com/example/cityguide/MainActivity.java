package com.example.cityguide;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.cityguide.ui.home.HomeFragment;
import com.example.cityguide.ui.map.MapFragment;
import com.example.cityguide.ui.favorites.FavoritesFragment;
import com.example.cityguide.ui.profile.ProfileFragment;
import com.example.cityguide.ui.attractions.AttractionListFragment;
import com.example.cityguide.ui.attractions.AddEditAttractionFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
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

    // 🔹 Публичный метод для загрузки фрагментов (используется из HomeFragment)
    public void loadAttractionFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null); // 🔹 Добавляем в стек для возврата назад
        transaction.commit();
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}