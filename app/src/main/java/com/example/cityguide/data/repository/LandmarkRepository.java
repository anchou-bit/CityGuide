package com.example.cityguide.data.repository;

import com.example.cityguide.data.api.LandmarkApiService;
import com.example.cityguide.data.api.RetrofitClient;
import com.example.cityguide.data.models.Landmark;
import com.example.cityguide.data.models.Review;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class LandmarkRepository {
    private LandmarkApiService apiService;

    public LandmarkRepository() {
        apiService = RetrofitClient.getInstance().getApiService();
    }

    public List<Landmark> getAllLandmarks() throws IOException {
        Response<List<Landmark>> response = apiService.getAllLandmarks().execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new IOException("Ошибка сервера: " + response.code());
        }
    }

    public List<Landmark> getNearbyLandmarks(double lat, double lng) throws IOException {
        Response<List<Landmark>> response = apiService.getNearbyLandmarks(lat, lng, 5000).execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new IOException("Ошибка сервера: " + response.code());
        }
    }

    public List<Landmark> searchLandmarks(String query) throws IOException {
        Response<List<Landmark>> response = apiService.searchLandmarks(query).execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new IOException("Ошибка сервера: " + response.code());
        }
    }

    public List<Review> getReviews(String landmarkId) throws IOException {
        Response<List<Review>> response = apiService.getReviews(landmarkId).execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new IOException("Ошибка сервера: " + response.code());
        }
    }
}