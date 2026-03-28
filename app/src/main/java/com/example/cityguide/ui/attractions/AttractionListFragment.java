package com.example.cityguide.ui.attractions;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cityguide.MainActivity;
import com.example.cityguide.R;
import com.example.cityguide.data.models.Attraction;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AttractionListFragment extends Fragment implements AttractionAdapter.OnItemClickListener {

    private AttractionViewModel viewModel;
    private AttractionAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_attraction_list, container, false);

        // 🔹 Кнопка "Назад"
        ImageButton btnBack = root.findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> goBack());
        }

        // Инициализация RecyclerView
        recyclerView = root.findViewById(R.id.recyclerViewAttractions);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Создание адаптера
        adapter = new AttractionAdapter();
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        // FloatingActionButton для добавления
        FloatingActionButton fabAdd = root.findViewById(R.id.fabAddAttraction);
        fabAdd.setOnClickListener(v -> openAddEditFragment(-1));

        // Инициализация ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(AttractionViewModel.class);

        // Наблюдение за данными
        viewModel.getAllAttractions().observe(getViewLifecycleOwner(), attractions -> {
            adapter.setAttractions(attractions);
        });

        return root;
    }

    // 🔹 Метод возврата назад
    private void goBack() {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    // 🔹 Открытие фрагмента добавления/редактирования
    private void openAddEditFragment(int attractionId) {
        AddEditAttractionFragment fragment = new AddEditAttractionFragment();
        if (attractionId != -1) {
            Bundle bundle = new Bundle();
            bundle.putInt("attraction_id", attractionId);
            fragment.setArguments(bundle);
        }

        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).loadAttractionFragment(fragment);
        }
    }

    @Override
    public void onItemClick(Attraction attraction) {
        // Можно открыть детали или просто редактирование
        openAddEditFragment(attraction.getId());
    }

    @Override
    public void onEditClick(Attraction attraction) {
        openAddEditFragment(attraction.getId());
    }

    @Override
    public void onDeleteClick(Attraction attraction) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Удаление")
                .setMessage("Вы уверены, что хотите удалить \"" + attraction.getName() + "\"?")
                .setPositiveButton("Удалить", (dialog, which) -> {
                    viewModel.delete(attraction);
                    Toast.makeText(getContext(), "Удалено", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Отмена", null)
                .show();
    }
}