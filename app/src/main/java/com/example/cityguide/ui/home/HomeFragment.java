package com.example.cityguide.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cityguide.MainActivity;
import com.example.cityguide.R;
import com.example.cityguide.ui.attractions.AttractionListFragment;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // 🔹 Настройка кнопки "Каталог достопримечательностей"
        Button btnAttractions = root.findViewById(R.id.btnAttractions);
        if (btnAttractions != null) {
            btnAttractions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Переход к каталогу через MainActivity
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).loadAttractionFragment(new AttractionListFragment());
                    }
                }
            });
        }

        return root;
    }
}