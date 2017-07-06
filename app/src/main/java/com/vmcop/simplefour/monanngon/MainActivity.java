package com.vmcop.simplefour.monanngon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends Activity {
    // ADMOB
    //private static final String  INTERSTITIALAD_ID = "ca-app-pub-8354689046611467/1103219037";

    private ArrayList<BeanPost> beanPostArrayList;

    private GridView gridView;

    //private InterstitialAd mInterstitialAd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                intent.putExtras(bundle);

                startActivityForResult(intent, 0);

                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

            }
        });

        // QUANG CAO START
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(INTERSTITIALAD_ID);
//        requestNewInterstitial();
//
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                if (mInterstitialAd.isLoaded()) {
//                    mInterstitialAd.show();
//                }
//            }
//            @Override
//            public void onAdClosed() {
//
//            }
//            @Override
//            public void onAdOpened() {}
//            @Override
//            public void onAdFailedToLoad(int errorCode) {}
//
//        });
        // QUANG CAO END

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                Type listType = new TypeToken<ArrayList<BeanPost>>() {
                }.getType();
                beanPostArrayList = new GsonBuilder().create().fromJson(Util.loadJSONFromAsset(getAssets()), listType);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (beanPostArrayList != null) {
                    gridView.setAdapter(new ImageAdapter(MainActivity.this, beanPostArrayList));
                }
            }

        }.execute();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                showDialog(MainActivity.this, "", "Bạn muốn đóng ứng dụng?");
                AppRater.app_launched(MainActivity.this);
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showDialog(Context context, String inTitle, String inMessage) {
        // show a dialog notice no image found
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(inTitle);
        builder.setMessage(inMessage);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

//    private void requestNewInterstitial() {
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice("91BAF0D14311747AD628F5A5F9629E31")
//                .build();
//        mInterstitialAd.loadAd(adRequest);
//    }
}
