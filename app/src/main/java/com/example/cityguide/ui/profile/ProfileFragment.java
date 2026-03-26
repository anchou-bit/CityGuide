package com.example.cityguide.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.cityguide.R;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Этот R.layout.fragment_profile теперь существует!
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}