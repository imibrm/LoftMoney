package com.imibragimov.loftmoney.screens.main;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.imibragimov.loftmoney.AddItemActivity;
import com.imibragimov.loftmoney.R;
import com.imibragimov.loftmoney.screens.dashboard.DashboardFragment;
import com.imibragimov.loftmoney.screens.money.BudgetFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    private FrameLayout containerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        containerView = findViewById(R.id.container_view);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_view, new DashboardFragment())
                .commitNow();
    }
}