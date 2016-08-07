package com.chuan.beautifulgirls.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chuan.beautifulgirls.R;
import com.chuan.beautifulgirls.SecondActivity;
import com.chuan.beautifulgirls.utils.MyApplication;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentViewPagerItem extends Fragment{
	private String url;
	private String number;
	public FragmentViewPagerItem(String url,String number) {
		this.url = url;
		this.number = number;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		super.onCreateView(inflater, container, savedInstanceState);
		
		View view = inflater.inflate(R.layout.picture_fragment, container, false);
		ImageView img = (ImageView) view.findViewById(R.id.my_img);
		TextView text = (TextView) view.findViewById(R.id.img_number);
		TextView save = (TextView) view.findViewById(R.id.save);
		TextView back = (TextView) view.findViewById(R.id.back);
		
		text.setText(number);
		Glide.with(this).load(url).
		asBitmap().fitCenter().
		skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL).
		into(img);
		
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Glide.with(MyApplication.getContext()).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {  
	                @Override  
	                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {  
	                	
	                	File dir = new File(Environment.getExternalStorageDirectory()+"/MyGirls/");
	                	if(!dir.exists()){
	                		dir.mkdir();
	                	}
	                	File file = new File(Environment.getExternalStorageDirectory()+"/MyGirls/",
	                			url.substring(url.lastIndexOf("/")+1,url.length()));	                	

	    				try {
	    					FileOutputStream fo = new FileOutputStream(file);			
	    					resource.compress(Bitmap.CompressFormat.JPEG, 100, fo);
	    					fo.flush();
	    					fo.close();
	    				} catch (FileNotFoundException e) {
	    					e.printStackTrace();
	    				} catch (IOException e) {
	    					e.printStackTrace();
	    				}
	                }  
	            });
				
				Toast.makeText(MyApplication.getContext(), "图片已经保存至MyGirls文件夹", Toast.LENGTH_SHORT).show();				
			}
		});
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SecondActivity activity = (SecondActivity) getActivity();
				activity.showFragment("FragmentFlow","");			
			}
		});
		
		return view;
	}
}
