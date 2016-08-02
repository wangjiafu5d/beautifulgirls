package com.chuan.beautifulgirls.fragment;

import java.util.ArrayList;
import java.util.List;

import com.chuan.beautifulgirls.R;

import com.chuan.beautifulgirls.adapter.MyViewPagerAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment_viewpager extends Fragment{
	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<String> urls = new ArrayList<String>();
	public Activity activity;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view =inflater.inflate(R.layout.viewpager_layout, container, false);
		urls.add("http://ww3.sinaimg.cn/mw1024/9505c694gw1em9oo62xn7j20pt0h6ac3.jpg");
		urls.add("http://ww3.sinaimg.cn/mw1024/9505c694jw1ed73sr6je0j20mi0u0q5c.jpg");
		urls.add("http://ww2.sinaimg.cn/mw1024/62283e37jw1f20sqjj4x8j20qo143wox.jpg");
		urls.add("http://ww3.sinaimg.cn/mw1024/62283e37jw1f0s3x0sqdsj20lc0sggpw.jpg");
		urls.add("http://ww3.sinaimg.cn/mw1024/62283e37jw1etxl0b9y56j20lc0sg78s.jpg");
		urls.add("http://ww4.sinaimg.cn/mw1024/62283e37tw1eg9ik5pmy3j21kw2dcanh.jpg");
		urls.add("http://ww3.sinaimg.cn/mw1024/62283e37tw1eg9ijz9jemj21kw11xdnv.jpg");
		urls.add("http://ww1.sinaimg.cn/mw1024/62283e37tw1eg9ijp7tssj21kw2dch0h.jpg");
		urls.add("http://ww1.sinaimg.cn/mw1024/62283e37tw1eg9ijp7tssj21kw2dch0h.jpg");
		urls.add("http://ww1.sinaimg.cn/mw1024/62283e37tw1eg9ijp7tssj21kw2dch0h.jpg");
		urls.add("http://ww1.sinaimg.cn/mw1024/62283e37tw1eg9ijp7tssj21kw2dch0h.jpg");
		urls.add("http://ww3.sinaimg.cn/mw1024/62283e37jw1f0s3x0sqdsj20lc0sggpw.jpg");
		urls.add("http://ww1.sinaimg.cn/mw1024/be447c45gw1f4p2zwhz43j21kw11xwnu.jpg");
		urls.add("http://ww1.sinaimg.cn/mw1024/be447c45gw1f4p2zwhz43j21kw11xwnu.jpg");
		urls.add("http://ww1.sinaimg.cn/mw1024/be447c45gw1f4p34n9rf1j21kw2dcarm.jpg");
		urls.add("http://ww1.sinaimg.cn/mw1024/be447c45gw1f3ntze3ea6j20f00jzwgk.jpg");
		for (int i = 0; i < 100; i++) {
			urls.add("http://ww1.sinaimg.cn/mw1024/be447c45gw1f3ntze3ea6j20f00jzwgk.jpg");
		}
		
		mViewPager = (ViewPager) view.findViewById(R.id.my_viewpager);
		FragmentManager fm =  getChildFragmentManager();		
		mAdapter = new MyViewPagerAdapter(fm, urls);
		mViewPager.setAdapter(mAdapter);
		mViewPager.setCurrentItem(1);
		
		
		
		
		
		return view;
	}
}
