package com.example.cityguide.ui.attractions;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cityguide.data.models.Attraction;
import com.example.cityguide.data.repository.AttractionRepository;

import java.util.List;

public class AttractionViewModel extends AndroidViewModel {
    private final AttractionRepository repository;
    private final LiveData<List<Attraction>> allAttractions;

    public AttractionViewModel(@NonNull Application application) {
        super(application);
        repository = new AttractionRepository(application);
        allAttractions = repository.getAllAttractions();
    }

    public LiveData<List<Attraction>> getAllAttractions() {
        return allAttractions;
    }

    public void insert(Attraction attraction) {
        repository.insert(attraction);
    }

    public void update(Attraction attraction) {
        repository.update(attraction);
    }

    public void delete(Attraction attraction) {
        repository.delete(attraction);
    }
}