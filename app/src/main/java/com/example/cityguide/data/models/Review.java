package com.example.cityguide.data.models;

import java.util.List;

public class Review {
    private String id;
    private String userId;
    private String userName;
    private String landmarkId;
    private float rating;
    private String comment;
    private String date;
    private List<String> photos;

    public Review() {}

    // Геттеры и сеттеры
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getLandmarkId() { return landmarkId; }
    public void setLandmarkId(String landmarkId) { this.landmarkId = landmarkId; }

    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public List<String> getPhotos() { return photos; }
    public void setPhotos(List<String> photos) { this.photos = photos; }
}