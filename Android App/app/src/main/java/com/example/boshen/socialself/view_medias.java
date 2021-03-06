package com.example.boshen.socialself;

import android.support.v4.app.Fragment;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.boshen.socialself.fragments.WebFragment;
import com.example.boshen.socialself.fragments.ContactFragment;

import org.json.JSONObject;

//this activity shows all the accounts of a user after their QR code is scanned, so that our user can add them
public class view_medias extends AppCompatActivity{
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.

    // declare variables
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String id, fb_name, insta_name, twitter_name, linkedin_name, email, phone;
    JSONObject result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_view_medias);

        //get the data that was passed from the QR code scan
        String data = getIntent().getExtras().getString("data");
        try{
            result = new JSONObject(data);
            id = result.getString("id");
            fb_name = result.getString("fb_name");
            insta_name = result.getString("insta_name");
            twitter_name = result.getString("twitter_name");
            linkedin_name = result.getString("linkedin_name");
            email = result.getString("email");
            phone = result.getString("phone");

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        //sets up the web pages
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        //sets up the tabs
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //if facebook name exists (and is valid), display fb tab
        if(fb_name.length()>1){
            adapter.addFragment(new WebFragment(), "Facebook", "https://m.facebook.com/"+fb_name);
        }
        //same with insta, twitter, linkedin
        if(insta_name.length()>1){
            adapter.addFragment(new WebFragment(), "Instagram", "https://www.instagram.com/"+insta_name);
        }
        if(twitter_name.length()>1){
            adapter.addFragment(new WebFragment(), "Twitter", "https://m.twitter.com/"+twitter_name);
        }
        if(linkedin_name.length()>1){
            adapter.addFragment(new WebFragment(), "Linkedin", "https://www.linkedin.com/in/"+linkedin_name);
        }

        //if email or phone number exists, display the contact information tab
        if(email.length()>6 || phone.length()>1) {
            ContactFragment contactFragment = new ContactFragment();
            adapter.mFragmentList.add(contactFragment);
            adapter.mFragmentTitleList.add("Contact Info");

            Bundle bundle = new Bundle();
            bundle.putString("email", email);
            bundle.putString("phone", phone);

            contactFragment.setArguments(bundle);
        }


        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

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

        //adds a fragment to the tabs
        public void addFragment(Fragment fragment, String title, String url) {

            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            fragment.setArguments(bundle);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}

