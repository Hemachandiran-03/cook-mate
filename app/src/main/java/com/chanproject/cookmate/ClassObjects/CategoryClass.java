package com.chanproject.cookmate.ClassObjects;

import androidx.annotation.DrawableRes;

public class CategoryClass {

    private String categoryName;
    private int imagePath;

    public CategoryClass(String categoryName, int imagePath) {
        this.categoryName = categoryName;
        this.imagePath = imagePath;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getImagePath() {
        return imagePath;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }
}
