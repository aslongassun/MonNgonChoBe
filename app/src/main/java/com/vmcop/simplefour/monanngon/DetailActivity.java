package com.vmcop.simplefour.monanngon;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vmcop.simplefour.monanngon.model.BeanPost;
import com.vmcop.simplefour.monanngon.util.Util;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.vmcop.simplefour.monanngon.MainActivity.*;
import static com.vmcop.simplefour.monanngon.util.Util.*;

public class DetailActivity extends Activity {
	
	private ArrayList<BeanPost> beanPostArrayList;
	private BeanPost currentBean;
    private Integer position;
    private String jsonFileName;
    
    private TextView nguyenLieuTextView;
    private TextView cachLamTextView;
    private TextView nguonTextView;
    private ImageView imageMonAn;
    private TextView labelTitle;
    private TextView labelNguyenLieu;
    private TextView labelCachLam;
    private Typeface typeface_detail_monan;
    private Typeface typeface_title_name_monan;
    private Typeface typeface_title_child_monan;
    private AdView mAdView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_detail);

        typeface_detail_monan = Typeface.createFromAsset(getAssets(), Util.CONS_FONT_DETAIL_MONAN);
        typeface_title_name_monan = Typeface.createFromAsset(getAssets(), Util.CONS_FONT_TITLE_NAME_MONAN);
        typeface_title_child_monan = Typeface.createFromAsset(getAssets(), Util.CONS_FONT_TTILE_CHILD_MONAN);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(DEVICE_ID)
                .build();
        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mAdView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAdFailedToLoad(int error) {
                mAdView.setVisibility(View.GONE);
            }
        });

		new AsyncTask<Void,Void,Void>(){
			
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // CONTENT
                nguyenLieuTextView = (TextView) findViewById(R.id.nguyenLieuId);
        		cachLamTextView = (TextView) findViewById(R.id.cachLamId);
        		nguonTextView = (TextView) findViewById(R.id.nguonTkId);
        		imageMonAn = (ImageView) findViewById(R.id.imageMonan);
                // HEADER
                labelTitle = (TextView) findViewById(R.id.tenMonId);
                labelNguyenLieu = (TextView) findViewById(R.id.lbnguyenlieu);
                labelCachLam = (TextView) findViewById(R.id.lbcachlam);
                // FONT
                nguyenLieuTextView.setTypeface(typeface_detail_monan);
                cachLamTextView.setTypeface(typeface_detail_monan);
                nguonTextView.setTypeface(typeface_detail_monan);
                labelTitle.setTypeface(typeface_title_name_monan);
                labelNguyenLieu.setTypeface(typeface_title_child_monan);
                labelCachLam.setTypeface(typeface_title_child_monan);

        		Bundle bundle = getIntent().getExtras();
        		position  = bundle.getInt("position");
                jsonFileName = bundle.getString("jsonfile");
            }

            @Override
            protected Void doInBackground(Void... voids) {
                Type listType = new TypeToken<ArrayList<BeanPost>>(){}.getType();
                beanPostArrayList = new GsonBuilder().create().fromJson(Util.loadJSONFromAsset(getAssets(),jsonFileName + ".json"), listType);
                if(beanPostArrayList != null && !beanPostArrayList.isEmpty()){
                	currentBean = beanPostArrayList.get(position);
                }
                return null;
            }
            
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(currentBean != null){
                	nguyenLieuTextView.setText(Util.getJSONContent(currentBean.getNguyen_lieu()));
                	cachLamTextView.setText(Util.getJSONContent(currentBean.getCach_lam()));
                	nguonTextView.setText(currentBean.getNguon_tk());

                    Glide.with(DetailActivity.this)
                            .load(Uri.parse(Util.getImageUrl(jsonFileName,currentBean.getImage_name())))
                            .into(imageMonAn);

                    labelTitle.setText(currentBean.getTitle().toUpperCase());
                }
            }
        }.execute();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    switch(keyCode){
            case KeyEvent.KEYCODE_BACK:
                Boolean isShowAdv = false;
                Long calTime = time_show_ad + (MINUTE_SHOW_AD * 60 * 1000);
                if(mInterstitialAd.isLoaded() && (System.currentTimeMillis() >= calTime)){
                    mInterstitialAd.show();
                    time_show_ad = System.currentTimeMillis();
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putLong("time_show_ad", time_show_ad);
                    editor.commit();
                }
                else {
                    finish();
                    overridePendingTransition(R.anim.re_slide_in, R.anim.re_slide_out);
                }
                return true;
	    } 
	    return super.onKeyDown(keyCode, event);
	}

}
