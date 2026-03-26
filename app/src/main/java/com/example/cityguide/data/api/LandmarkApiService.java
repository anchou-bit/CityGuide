package com.example.cityguide.data.api;

import com.example.cityguide.data.models.Landmark;
import com.example.cityguide.data.models.Review;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LandmarkApiService {

    @GET("landmarks")
    Call<List<Landmark>> getAllLandmarks();

    @GET("landmarks/{id}")
    Call<Landmark> getLandmarkById(@Path("id") String landmarkId);

    @GET("landmarks/nearby")
    Call<List<Landmark>> getNearbyLandmarks(
            @Query("lat") double latitude,
            @Query("lng") double longitude,
            @Query("radius") int radius
    );

    @GET("landmarks/search")
    Call<List<Landmark>> searchLandmarks(@Query("query") String query);

    @GET("landmarks/category/{category}")
    Call<List<Landmark>> getLandmarksByCategory(@Path("category") String category);

    @POST("users/{userId}/favorites/{landmarkId}")
    Call<Void> addToFavorites(
            @Path("userId") String userId,
            @Path("landmarkId") String landmarkId
    );

    @DELETE("users/{userId}/favorites/{landmarkId}")
    Call<Void> removeFromFavorites(
            @Path("userId") String userId,
            @Path("landmarkId") String landmarkId
    );

    @GET("landmarks/{landmarkId}/reviews")
    Call<List<Review>> getReviews(@Path("landmarkId") String landmarkId);

    @POST("landmarks/{landmarkId}/reviews")
    Call<Review> addReview(
            @Path("landmarkId") String landmarkId,
            @Body Review review
    );
}