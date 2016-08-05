package com.chuan.beautifulgirls.fragment;

import java.util.ArrayList;
import java.util.List;

import com.chuan.beautifulgirls.R;

import com.chuan.beautifulgirls.adapter.MyViewPagerAdapter;

import android.app.Activity;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Fragment_viewpager extends Fragment {
	private ViewPager mViewPager;
	private MyViewPagerAdapter mAdapter;
	private List<String> urls = new ArrayList<String>();
	public Activity activity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.viewpager_layout, container, false);

		mViewPager = (ViewPager) view.findViewById(R.id.my_viewpager);
		FragmentManager fm = getChildFragmentManager();
		mAdapter = new MyViewPagerAdapter(fm, urls);
		mViewPager.setAdapter(mAdapter);
		mViewPager.setCurrentItem(0);

		return view;
	}

	public void setUrls(List<String> urls) {
		if (urls.size() >= 2) {
			this.urls = urls.subList(1, urls.size());
		}else {
			this.urls = urls;
		}
	}
//	public void update(final List<String> newUrls){	
//		
//		new Handler().post(new Runnable() {
//			@Override
//			public void run() {
//				mViewPager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager(), newUrls));
//				mViewPager.setCurrentItem(0);
//							
//			}
//
//		});
//	}
	
}
