package com.chuan.beautifulgirls.fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chuan.beautifulgirls.R;
import com.chuan.beautifulgirls.SecondActivity;
import com.chuan.beautifulgirls.utils.MyApplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_viewpager_item extends Fragment{
	private String url;
	private String number;
	public Fragment_viewpager_item(String url,String number) {
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
		skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESULT).
		into(img);
		
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(MyApplication.getContext(), "save", Toast.LENGTH_SHORT).show();				
			}
		});
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SecondActivity activity = (SecondActivity) getActivity();
				activity.showFragment(activity.fragment_flow,"");			
			}
		});
		
		return view;
	}
}
