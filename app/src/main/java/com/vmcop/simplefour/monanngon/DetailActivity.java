package com.vmcop.simplefour.monanngon;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DetailActivity extends Activity {
	
	private ArrayList<BeanPost> beanPostArrayList;
	private BeanPost currentBean;
    private Integer position;
    
    private TextView nguyenLieuTextView;
    private TextView cachLamTextView;
    private TextView nguonTextView;
    private ImageView imageMonAn;
    private TextView labelTitle;
    private TextView labelNguyenLieu;
    private TextView labelCachLam;
    
    // ADMOB
    private static final String  INTERSTITIALAD_ID = "ca-app-pub-8354689046611467/1103219037";
    private static final int MINUTE_SHOW_AD = 3;//Min number of minutes
    private final static int LAUNCHES_UNTIL_AD = 2;//Min number of launches
    private long time_show_ad;
    private long launch_count;
    private SharedPreferences prefs;
    InterstitialAd mInterstitialAd;

    private Typeface typeface_type1_regular;
    private Typeface typeface_type2_bold;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_detail);
		
		//Toast.makeText(DetailActivity.this, "" + position, Toast.LENGTH_SHORT).show();
		// BANNER
        /*
		AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
			        .addTestDevice("91BAF0D14311747AD628F5A5F9629E31")
			        .build();
        mAdView.loadAd(adRequest);
        */
        typeface_type1_regular = Typeface.createFromAsset(getAssets(), Util.CONS_FONT_TYPE1_REGULAR);
        typeface_type2_bold = Typeface.createFromAsset(getAssets(), Util.CONS_FONT_TYPE2_BOLD);

        prefs = DetailActivity.this.getSharedPreferences("apprater", 0);
        time_show_ad = prefs.getLong("time_show_ad", 0);
        launch_count = prefs.getLong("launch_count", 0);
		mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(INTERSTITIALAD_ID);
        requestNewInterstitial();

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                finish();
            	overridePendingTransition(R.anim.re_slide_in, R.anim.re_slide_out);
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

                //--- FONT---//
                nguyenLieuTextView.setTypeface(typeface_type1_regular);
                cachLamTextView.setTypeface(typeface_type1_regular);
                nguonTextView.setTypeface(typeface_type1_regular);

                labelTitle.setTypeface(typeface_type2_bold);
                labelNguyenLieu.setTypeface(typeface_type2_bold);
                labelCachLam.setTypeface(typeface_type2_bold);
                //------------//

        		Bundle bundle = getIntent().getExtras();
        		position  = bundle.getInt("position");
        		
            }
            
            @Override
            protected Void doInBackground(Void... voids) {
                Type listType = new TypeToken<ArrayList<BeanPost>>(){}.getType();
                beanPostArrayList = new GsonBuilder().create().fromJson(Util.loadJSONFromAsset(getAssets()), listType);
                
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
                	imageMonAn.setImageResource(Util.getImageId(DetailActivity.this, currentBean.getImage_name()));
                    labelTitle.setText(Util.getTenMon(currentBean.getTitle()));
                }
            }
            
        }.execute();
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    switch(keyCode){
            case KeyEvent.KEYCODE_BACK:
                if (mInterstitialAd.isLoaded() && launch_count >= LAUNCHES_UNTIL_AD && (System.currentTimeMillis() >= time_show_ad +
                        (MINUTE_SHOW_AD * 60 * 1000))) {
                    mInterstitialAd.show();

                    time_show_ad = System.currentTimeMillis();
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putLong("time_show_ad", time_show_ad);
                    editor.commit();

                } else {
                    finish();
                    overridePendingTransition(R.anim.re_slide_in, R.anim.re_slide_out);
                }
                return true;
	    } 
	    return super.onKeyDown(keyCode, event);
	}

	private void requestNewInterstitial() {
	    AdRequest adRequest = new AdRequest.Builder()
	              .addTestDevice("91BAF0D14311747AD628F5A5F9629E31")
	              .build();
	    mInterstitialAd.loadAd(adRequest);
	}
}
