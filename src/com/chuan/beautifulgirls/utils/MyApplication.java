package com.chuan.beautifulgirls.utils;


import java.util.ArrayList;
import java.util.List;

import com.chuan.beautifulgirls.MainActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;

public class MyApplication extends Application{
	
	private static Context context;
	private static List<Activity> activities = new ArrayList<Activity>();
	
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
		AlertDialog.Builder dialog = new AlertDialog.Builder(activity,AlertDialog.THEME_TRADITIONAL);
//		dialog.setTitle("提示");
		dialog.setMessage("确认退出程序?");
//		dialog.setCancelable(false);
		
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				activity.finish();
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
	}
}
