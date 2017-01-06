package com.vmcop.simplefour.monanngon;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.app.Activity;
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

public class DetailActivity extends Activity {
	
	private ArrayList<BeanPost> beanPostArrayList;
	private BeanPost currentBean;
    private Integer position;
    
    private TextView nguyenLieuTextView;
    private TextView cachLamTextView;
    private TextView nguonTextView;
    private ImageView imageMonAn;
    
    // ADMOB
    public static final String  INTERSTITIALAD_ID = "ca-app-pub-8354689046611467/1103219037";
    // 2017/01/06 Commented start
 	// InterstitialAd mInterstitialAd;
    // 2017/01/06 Commented end

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
		
		// 2017/01/06 Commented start
		/*mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(INTERSTITIALAD_ID);
        requestNewInterstitial();


        
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                finish();
            	overridePendingTransition(R.anim.re_slide_in, R.anim.re_slide_out);
            }
        });*/
        // 2017/01/06 Commented end
		
		new AsyncTask<Void,Void,Void>(){
			
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                
                nguyenLieuTextView = (TextView) findViewById(R.id.nguyenLieuId);
        		cachLamTextView = (TextView) findViewById(R.id.cachLamId);
        		nguonTextView = (TextView) findViewById(R.id.nguonTkId);
        		imageMonAn = (ImageView) findViewById(R.id.imageMonan);
                
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
                }
            }
            
        }.execute();
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    switch(keyCode){
	    case KeyEvent.KEYCODE_BACK:
            // 2017/01/06 modified start
	    	/*if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
            	finish();
            	overridePendingTransition(R.anim.re_slide_in, R.anim.re_slide_out);
            }*/
            finish();
            overridePendingTransition(R.anim.re_slide_in, R.anim.re_slide_out);
            // 2017/01/06 modified end
	        return true;
	    } 
	    return super.onKeyDown(keyCode, event);
	}

    // 2017/01/06 Commented start
	/*private void requestNewInterstitial() {
	    AdRequest adRequest = new AdRequest.Builder()
	              .addTestDevice("91BAF0D14311747AD628F5A5F9629E31")
	              .build();
	    mInterstitialAd.loadAd(adRequest);
	}*/
    // 2017/01/06 Commented end
}
