package com.example.cityguide.data.models;

public class Landmark {
    private String id;
    private String name;
    private String description;
    private double latitude;
    private double longitude;
    private String category;
    private float rating;
    private String imageUrl;
    private String workingHours;
    private String address;
    private boolean isFavorite;

    public Landmark() {
        // Пустой конструктор для Firebase/Retrofit
    }

    public Landmark(String id, String name, String description, double latitude,
                    double longitude, String category, float rating,
                    String imageUrl, String workingHours, String address) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.workingHours = workingHours;
        this.address = address;
        this.isFavorite = false;
    }

    // Геттеры и сеттеры (нажмите Alt+Insert → Getter and Setter)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getWorkingHours() { return workingHours; }
    public void setWorkingHours(String workingHours) { this.workingHours = workingHours; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
}