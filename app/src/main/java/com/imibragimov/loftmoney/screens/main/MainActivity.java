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
import com.google.android.material.tabs.TabLayout;


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


    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tab_view);
        ViewPager viewPager = findViewById(R.id.view_pager);

        viewPager.setAdapter(new BudgetPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

        FloatingActionButton floatingActionButton = findViewById(R.id.add_new_item);
        floatingActionButton.setOnClickListener(v -> {
            final int activeFragmentIndex = viewPager.getCurrentItem();
            Fragment activeFragment = getSupportFragmentManager().getFragments().get(activeFragmentIndex);
            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            intent.putExtra("activeFragmentIndex", activeFragmentIndex);
            activeFragment.startActivityForResult(intent, BudgetFragment.REQUEST_CODE);
        });

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(R.string.expenses);
        tabLayout.getTabAt(1).setText(R.string.incomes);

    }

    static class BudgetPagerAdapter extends FragmentPagerAdapter {

        public BudgetPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (position < 2) {
                return BudgetFragment.newInstance(position);
            } else
                return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
*/



}
