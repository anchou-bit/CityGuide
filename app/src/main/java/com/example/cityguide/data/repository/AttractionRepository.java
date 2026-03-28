package com.example.cityguide.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.cityguide.data.AttractionDao;
import com.example.cityguide.data.AppDatabase;
import com.example.cityguide.data.models.Attraction;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AttractionRepository {
    private final AttractionDao attractionDao;
    private final LiveData<List<Attraction>> allAttractions;
    private final ExecutorService executor;

    public AttractionRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        attractionDao = database.attractionDao();
        allAttractions = attractionDao.getAllAttractions();
        executor = Executors.newFixedThreadPool(4);
    }

    public LiveData<List<Attraction>> getAllAttractions() {
        return allAttractions;
    }

    public void insert(Attraction attraction) {
        executor.execute(() -> attractionDao.insert(attraction));
    }

    public void update(Attraction attraction) {
        executor.execute(() -> attractionDao.update(attraction));
    }

    public void delete(Attraction attraction) {
        executor.execute(() -> attractionDao.delete(attraction));
    }
}