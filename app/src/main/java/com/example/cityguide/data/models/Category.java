package com.example.cityguide.data.models;

public enum Category {
    MUSEUM("Музей"),
    PARK("Парк"),
    ARCHITECTURE("Архитектура"),
    MONUMENT("Памятник"),
    RESTAURANT("Ресторан"),
    OTHER("Другое");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if (category.displayName.equalsIgnoreCase(text)) {
                return category;
            }
        }
        return OTHER;
    }
}