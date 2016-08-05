package com.chuan.beautifulgirls.adapter;

import java.util.ArrayList;
import java.util.List;

import com.chuan.beautifulgirls.fragment.Fragment_viewpager_item;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class MyViewPagerAdapter extends FragmentPagerAdapter{	
	
	public List<String> urls = new ArrayList<String>();
	
	
	public MyViewPagerAdapter(FragmentManager fm,List<String> urls) {
		super(fm);
		this.urls = urls;
	}

	@Override
	public Fragment getItem(int arg0) {
		String number = new String("/");
		number =  (arg0+1) + number +urls.size();
		Fragment fragment = new Fragment_viewpager_item(urls.get(arg0),number);
		
		
		return fragment;
	}

	@Override
	public int getCount() {
		
		return urls.size();
	}
	
//	@Override
//	public void notifyDataSetChanged() {
//		
//		super.notifyDataSetChanged();
//	}
//	@Override
//	public int getItemPosition(Object object) {
////		if(count>0){
////			count--;
//			  return POSITION_NONE;
////		}
////		return super.getItemPosition(object);
//	}
}
