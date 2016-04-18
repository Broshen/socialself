package com.example.boshen.socialself;

import android.support.v4.app.Fragment;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import com.example.boshen.socialself.fragments.WebFragment;

public class view_medias extends AppCompatActivity{
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_view_medias);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
       // Fragment fbFragment = new fbFragment();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new WebFragment(), "Facebook", "/alex.pantea.507");

        adapter.addFragment(new WebFragment(), "Twitter", "");

        adapter.addFragment(new WebFragment(), "Instagram", "");
       // /adapter.addFragment(new fbFragment(), "TWO");
      //  adapter.addFragment(new fbFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private final List<String> mFragmentUrlList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title, String url) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
            mFragmentUrlList.add(url);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}

