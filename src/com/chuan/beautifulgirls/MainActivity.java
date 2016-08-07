package com.chuan.beautifulgirls;


import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chuan.beautifulgirls.utils.HttpCallbackListener;
import com.chuan.beautifulgirls.utils.HttpUtil;
import com.chuan.beautifulgirls.utils.MyApplication;
import com.chuan.beautifulgirls.utils.PraseResponse;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
	private AlarmManager manager;
	private PendingIntent pi;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (MyApplication.containsActivity()) {
			MyApplication.addActivity(this);
			Intent i = new Intent("SecondActivity");
			startActivity(i);
			return ;
		}
		//设置全屏显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		updateUrl();		
		initViews();		
		startSecondActivity();		
		MyApplication.addActivity(this);
	}
	@Override
	protected void onPause() {		
		super.onPause();
		finish();
	}
	@Override
	public void onBackPressed() {
		if (manager != null) {
			manager.cancel(pi);
		}
		MyApplication.showFinishDialog(this);
	}
	
	protected void onDestroy() {
		if(manager!=null){	
			manager.cancel(pi);
			}
		super.onDestroy();
	}
    //初始化控件，加入跳过当前页面，启动活动2的监听
	private void initViews(){
		ImageView img = (ImageView) findViewById(R.id.home_img);
		TextView skip = (TextView) findViewById(R.id.start_second_activity);
		skip.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				manager.cancel(pi);
				Intent i = new Intent("SecondActivity");				
				startActivity(i);
			}
		});
		updateHomeImage(img);
	}
	//延时启动活动2
	public void startSecondActivity(){		
		manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		long triggerAtTime = System.currentTimeMillis() + 2000;
		Intent i = new Intent("SecondActivity");
		pi = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
		manager.set(AlarmManager.RTC, triggerAtTime, pi);
	}
	
	//获取载入图片，并显示
	private void updateHomeImage(ImageView view){
		
		SharedPreferences sp = getSharedPreferences("home_urls", MODE_PRIVATE);
		String url = sp.getString("url1", "empty");
		if(url.equals("empty")){
			url = String.valueOf(R.drawable.home);
		}
		Glide.with(this).load(url).
		asBitmap().
		error(R.drawable.home).
		skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL).
		centerCrop().
		into(view);
		
	}
	//访问网络获取图片分类URL地址和所有图片地址URL，保存至Application的成员静态变量
	private void updateUrl(){
		if(MyApplication.getUrl_list1().size()==0){
			String address = getString(R.string.address1);
			HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
				
				@Override
				public void onFinish(String response) {
					PraseResponse.handleResponse(response,MyApplication.getUrl_list1(),MyApplication.getUrl_map1());
					
				}
				
				@Override
				public void onError(Exception e) {				
				}
			});

			HttpUtil.sendHttpRequest(getString(R.string.address2), new HttpCallbackListener() {
				
				@Override
				public void onFinish(String response) {
					PraseResponse.handleResponse(response,MyApplication.getUrl_list2(),MyApplication.getUrl_map2());
					loadPictureCache();
				}
				
				@Override
				public void onError(Exception e) {				
				}
			});
		}
	}
	//预加载载入画面的图片，下次登录时显示
	private void loadPictureCache(){
		//从图片分类列表中随机选取图片类型，再在详细列表中随机选取一张图片缓存图片和保存地址到sharedPreference
		List<String> list = MyApplication.getUrl_list2();
		int i = (int) (Math.random()*list.size()/1);
		if (i>=0) {
			List<String> allPicturesList = MyApplication.getUrl_map2().get(list.get(i));
			int i2 = (int) (Math.random()*allPicturesList.size()/1);
			String url2 = allPicturesList.get(i2);
			Glide.with(this).load(url2).
			asBitmap().
			error(R.drawable.home).
			skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL).
			centerCrop();		
			SharedPreferences sp = getSharedPreferences("home_urls", MODE_PRIVATE);
			SharedPreferences.Editor editor = sp.edit();
			editor.putString("url1", url2);
			editor.commit();
		}
		
	}
	
}
