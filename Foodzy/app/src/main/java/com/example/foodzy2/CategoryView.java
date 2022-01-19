package com.example.foodzy2;
import model.Meals;

import java.util.List;
public interface CategoryView {
    void showLoading();
    void hideLoading();
    void setMeals(List<Meals.Meal> meals);
    void onErrorLoading(String message);
}
