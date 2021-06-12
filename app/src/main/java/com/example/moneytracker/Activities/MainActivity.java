package com.example.moneytracker.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.moneytracker.Fragments.DashboardFragment;
import com.example.moneytracker.Fragments.HistoryFragment;
import com.example.moneytracker.Fragments.CategoriesFragment;
import com.example.moneytracker.Fragments.SettingsFragment;
import com.example.moneytracker.Fragments.ViewPageAdapter;
import com.example.moneytracker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView=findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        viewPager=findViewById(R.id.fragment_container);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setUpAdapter(viewPager);
    }

    private void setUpAdapter(ViewPager viewPager){
        ViewPageAdapter viewPageAdapter= new ViewPageAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPageAdapter.addFragment(new DashboardFragment());
        viewPageAdapter.addFragment(new CategoriesFragment());
        viewPageAdapter.addFragment(new HistoryFragment());
        viewPageAdapter.addFragment(new SettingsFragment());
        viewPager.setAdapter(viewPageAdapter);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Log.d("DEBUG","Item clicked "+item.getItemId());
            switch (item.getItemId()){
                case R.id.nav_dashboard:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.nav_categories:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.nav_history:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.nav_settings:
                    viewPager.setCurrentItem(3);
                    return true;
                default:
                    return false;
            }
        }
    };

}