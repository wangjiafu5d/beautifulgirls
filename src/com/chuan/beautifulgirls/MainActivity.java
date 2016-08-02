package com.chuan.beautifulgirls;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chuan.beautifulgirls.utils.MyApplication;


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
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
	
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
		
		startSecondActivity();
		updateHomeImage(img);
		MyApplication.addActivity(this);
		
	}
	@Override
	protected void onPause() {		
		super.onPause();
		finish();
	}
	@Override
	public void onBackPressed() {	
		manager.cancel(pi);
		MyApplication.showFinishDialog(this);
	}
	@Override
	protected void onStop() {		
		super.onStop();
		
	};
	protected void onDestroy() {
		manager.cancel(pi);
		super.onDestroy();
	}
    
	public void startSecondActivity(){
		
		manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		long triggerAtTime = System.currentTimeMillis() + 2000;
		Intent i = new Intent("SecondActivity");
		pi = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
		manager.set(AlarmManager.RTC, triggerAtTime, pi);
	}
	
	private void updateHomeImage(ImageView view){
		
		SharedPreferences sp = getSharedPreferences("home_urls", MODE_PRIVATE);
		String url = sp.getString("url1", "empty");
		if(url.equals("empty")){
			url = String.valueOf(R.drawable.home);
		}
		Glide.with(this).load(url).
		asBitmap().
		error(R.drawable.home).
		skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESULT).
		centerCrop().
		into(view);
		
		String url2 = sp.getString("url2", "empty");
//		url2 = "http://ww1.sinaimg.cn/mw1024/60e95fd8jw1f3rq8k1pqsj20qo0zk45s.jpg";
		if(!url2.equals("empty")){
			
			Glide.with(this).load(url2).
			asBitmap().
			error(R.drawable.home).
			skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESULT).
			centerCrop();			

			SharedPreferences.Editor editor = sp.edit();
			editor.putString("url1", url2);
			editor.commit();
		}
	}
	
}
