package com.vmcop.simplefour.monanngon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.vmcop.simplefour.monanngon.tabfragment.TabFragment;
import com.vmcop.simplefour.monanngon.util.AppRater;

import java.util.ArrayList;
import java.util.List;

import static com.vmcop.simplefour.monanngon.util.Util.DEVICE_ID;
import static com.vmcop.simplefour.monanngon.util.Util.INTERSTITIALAD_ID;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static InterstitialAd mInterstitialAd;

    public static SharedPreferences prefs;
    public static long time_show_ad;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        prefs = this.getSharedPreferences("apprater", 0);
        time_show_ad = prefs.getLong("time_show_ad", 0);
        // QUANG CAO START
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(INTERSTITIALAD_ID);
        requestNewInterstitial();
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                overridePendingTransition(R.anim.re_slide_in, R.anim.re_slide_out);
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {}
        });
        // QUANG CAO END

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                showDialog(MainActivity.this, "", "Bạn muốn đóng ứng dụng ?");
                AppRater.app_launched(MainActivity.this);
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showDialog(Context context, String inTitle, String inMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(inTitle);
        builder.setMessage(inMessage);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {}
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        TabFragment tab1 = new TabFragment();
        tab1.setTabName("Món Ăn");
        tab1.setJSONFile("mon_an");

        TabFragment tab2 = new TabFragment();
        tab2.setTabName("Món Bánh");
        tab2.setJSONFile("mon_banh");

        adapter.addFragment(tab1, tab1.getTabName());
        adapter.addFragment(tab2, tab2.getTabName());

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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(DEVICE_ID)
                .build();
        mInterstitialAd.loadAd(adRequest);
    }
}

// TAB
//toolbar = (Toolbar) findViewById(R.id.toolbar);
//setSupportActionBar(toolbar);
//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//getSupportActionBar().setDisplayShowTitleEnabled(false);
//gridView = (GridView) findViewById(R.id.gridview);

// QUANG CAO START
        /*
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(INTERSTITIALAD_ID);
        requestNewInterstitial();

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
            @Override
            public void onAdClosed() {

            }
            @Override
            public void onAdOpened() {}
            @Override
            public void onAdFailedToLoad(int errorCode) {}

        });
        */
// QUANG CAO END