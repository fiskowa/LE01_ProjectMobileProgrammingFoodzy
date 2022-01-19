package com.example.foodzy2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.foodzy2.R;
import com.example.foodzy2.Utils;
import com.example.foodzy2.adapter.RecyclerViewHomeAdapter;
import com.example.foodzy2.adapter.ViewPagerHeaderAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.Categories;
import model.Meals;
import com.example.foodzy2.CategoryActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.util.List;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

public class HomePage extends AppCompatActivity implements HomeView {
    public static final String EXTRA_CATEGORY = "category";
    public static final String EXTRA_POSITION = "position";
    public static final String EXTRA_DETAIL = "detail";

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private Button btnToggleDark;


    //@BindView(R.id.viewPagerHeader)
    ViewPager viewPagerMeal;
    @BindView(R.id.recyclerCategory)
    RecyclerView recyclerViewCategory;

    HomePresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);

        presenter = new HomePresenter(this);
        presenter.getMeals();
        presenter.getCategories();

        // Nav drawer
        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.nav_home:

                        Intent intent = new Intent(HomePage.this, HomePage.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_category:
                        Intent intent1 = new Intent(HomePage.this , CategoryFragment.class);

                        startActivity(intent1);
                        break;
                    case R.id.nav_aboutUs:
                        Intent intent2 = new Intent(HomePage.this , AboutUs.class);

                        startActivity(intent2);
                        break;
                    case R.id.nav_login:
                        Intent intent3 = new Intent(HomePage.this , AccountActivity.class);

                        startActivity(intent3);
                        break;

                }
                return false;
            }
        });

              // BOTTOM NAVIGATION
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home2);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home2:
                        return true;
                    case R.id.category2:
                        startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.aboutUs2:
                        startActivity(new Intent(getApplicationContext(), AboutUs.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });




    }


    @Override
    public void showLoading() {
        //  findViewById(R.id.shimmerMeal).setVisibility(View.VISIBLE);
        findViewById(R.id.shimmerCategory).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
       //findViewById(R.id.shimmerMeal).setVisibility(View.GONE);
        findViewById(R.id.shimmerCategory).setVisibility(View.GONE);
    }

    @Override
    public void setMeal(List<Meals.Meal> meal) {
      ViewPagerHeaderAdapter headerAdapter = new ViewPagerHeaderAdapter(meal,this);
    viewPagerMeal.setAdapter(headerAdapter);
    viewPagerMeal.setPadding(20,0,150,0);
    headerAdapter.notifyDataSetChanged();


         headerAdapter.setOnItemClickListener((view, position) -> {
             TextView mealName = view.findViewById(R.id.mealName);
             Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
             intent.putExtra(EXTRA_DETAIL, mealName.getText().toString());
             startActivity(intent);
        });
    }

    @Override
    public void setCategory(List<Categories.Category> category) {
        RecyclerViewHomeAdapter homeAdapter = new RecyclerViewHomeAdapter(category, this);
        recyclerViewCategory.setAdapter(homeAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3,
                GridLayoutManager.VERTICAL, false);
        recyclerViewCategory.setLayoutManager(layoutManager);
        recyclerViewCategory.setNestedScrollingEnabled(true);
        homeAdapter.notifyDataSetChanged();

      homeAdapter.setOnItemClickListener((view, position) -> {
          Intent intent = new Intent(this, CategoryActivity.class);
         intent.putExtra(EXTRA_CATEGORY, (Serializable) category);
           intent.putExtra(EXTRA_POSITION, position);
          startActivity(intent);
       });
    }

    @Override
    public void onErrorLoading(String message) {
        Utils.showDialogMessage(this, "Title", message);
    }

    public void darkbutton(View view) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

    }

    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        actionBarDrawerToggle.syncState();

    }

}