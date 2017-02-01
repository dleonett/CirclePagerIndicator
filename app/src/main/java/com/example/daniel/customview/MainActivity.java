package com.example.daniel.customview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.circlesIndicator)
    CirclesIndicator circlesIndicator;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(PageFragment.newInstace("PAGE 1"));
        fragmentList.add(PageFragment.newInstace("PAGE 2"));
        fragmentList.add(PageFragment.newInstace("PAGE 3"));
        fragmentList.add(PageFragment.newInstace("PAGE 4"));

        viewPagerAdapter = new ViewPagerAdapter(this, fragmentList, getSupportFragmentManager());

        viewPager.setAdapter(viewPagerAdapter);
        circlesIndicator.setViewPager(viewPager);
    }

}
