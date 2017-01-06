package com.vmcop.simplefour.monanngon;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;

public class Util {
	public static final String CONS_JSON_PART = "+";
	public static final String CONS_SHOW_PART = "- ";
	public static String loadJSONFromAsset( AssetManager inAssetManager) {
        String json = null;
        try {
            InputStream is = inAssetManager.open("mon_an.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
       } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
	
	public static int getImageId(Context context, String imageName) {
	    return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
	}
	
	public static String getJSONContent(String contentData){
		String resultStr = "";
		if(isBlankString(contentData)){
			return resultStr;
		}
		
		String trimItem = "";
		String[] slipData =  contentData.split("\\"+CONS_JSON_PART);
		for(String item : slipData){
			trimItem = item.trim();
			if("".endsWith(trimItem)){
				continue;
			}
			if("".endsWith(resultStr)){
				resultStr += CONS_SHOW_PART + item.trim();
			} else {
				resultStr += "\n" + CONS_SHOW_PART + item.trim();
			}
		}
		return resultStr;
	}
	
	public static Boolean isBlankString(String inString){
		if(inString != null && !"".endsWith(inString.trim())){
			return false;
		}
		return true;
	}
	
}
