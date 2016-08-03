package com.chuan.beautifulgirls.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chuan.beautifulgirls.MainActivity;
import com.chuan.beautifulgirls.SecondActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;

public class MyApplication extends Application{
	
	private static Context context;
	private static List<Activity> activities = new ArrayList<Activity>();
	private static List<String> url_list = new ArrayList<String>();
	private static Map<String, List<String>> url_map = new HashMap<String, List<String>>();
//	public static boolean urlUpdateFlag = false;
	
	@Override
	public void onCreate() {
		context = getApplicationContext();
	}
	
	public static Context getContext(){
		return context;
	}
	public static void addActivity(Activity activity){
		activities.add(activity);
	}
	
	public static void  showFinishDialog(final Activity activity){
		AlertDialog.Builder dialog = new AlertDialog.Builder(activity,AlertDialog.THEME_HOLO_LIGHT);
//		dialog.setTitle("提示");
		dialog.setMessage("确认退出程序?");	
//		dialog.setCancelable(false);
		
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finishAll();
			}
		});
		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				if (activity instanceof MainActivity) {
				((MainActivity) activity).startSecondActivity();
				
				}
			}
		});
		dialog.show();
	}
	public static void finishAll(){
		for (Activity activity : activities) {
			activity.finish();			
		}
		activities.clear();
	}
	public static void removeActivity(Activity activity){
		if (activities.contains(activity)) {
			activities.remove(activity);
		}
	}
	public static boolean containsActivity(){
		for (Activity activity : activities) {
			if(activity instanceof SecondActivity){				
				return true;
			}
		}
		
		return false;
	}
	public static void addUrlList(String url){
//		Log.d("url", url);
		url_list.add(url);
	}
	
	public static void addUrlMap(String url,List<String> list){
		url_map.put(url, list);
	}
	public static List<String> getUrlList(){
		return url_list;
	}
	public static List<String> getChildUrlList(String url){		
		return url_map.get(url);
	}

//	public static boolean isUrlUpdateFlag() {
//		return urlUpdateFlag;
//	}
//
//	public static void setUrlUpdateFlag(boolean urlUpdateFlag) {
//		MyApplication.urlUpdateFlag = urlUpdateFlag;
//	}
}
