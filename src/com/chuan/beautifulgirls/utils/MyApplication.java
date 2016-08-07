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
	private static List<String> url_list1 = new ArrayList<String>();
	private static Map<String, List<String>> url_map1 = new HashMap<String, List<String>>();
	private static List<String> url_list2 = new ArrayList<String>();
	private static Map<String, List<String>> url_map2 = new HashMap<String, List<String>>();
	public static boolean urlUpdateFlag = false;
	
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
				url_list1.clear();
				url_map1.clear();
				url_list2.clear();
				url_map2.clear();
				urlUpdateFlag = false;
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
	
	
	public static List<String> getUrl_list1() {
		return url_list1;
	}

	public static List<String> getUrl_list2() {
		return url_list2;
	}

	public static Map<String, List<String>> getUrl_map2() {
		return url_map2;
	}

	public static List<String> getFlowUrlList(String url){		
		return url_map1.get(url);
	}
	public static List<String> getVPUrlList(String url){		
		return url_map2.get(url);
	}

	public static Map<String, List<String>> getUrl_map1() {
		return url_map1;
	}

}
