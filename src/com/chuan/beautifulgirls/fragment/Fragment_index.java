package com.chuan.beautifulgirls.fragment;

import java.util.ArrayList;
import java.util.List;

import com.chuan.beautifulgirls.R;
import com.chuan.beautifulgirls.SecondActivity;
import com.chuan.beautifulgirls.adapter.GridAdapter;

import com.chuan.beautifulgirls.utils.MyApplication;
import com.chuan.beautifulgirls.utils.OnItemClickListener;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Fragment_index extends Fragment{
	private RecyclerView mRecyclerView;
    public GridAdapter mAdapter;
    private GridLayoutManager  mLayoutManager;
    public List<String> urls = new ArrayList<String>(); 
   
   
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view =inflater.inflate(R.layout.index_grid_layout, container, false);
			if(MyApplication.urlUpdateFlag){
				List<String> newUrls = MyApplication.getUrl_list1();
				for (String string : newUrls) {
					urls.add(string);
				}				
			}
			
			initView(view);
			return view;
		}
		
		private void initView(View view) {
	        mRecyclerView = (RecyclerView) view.findViewById(R.id.index_recyclerView);
	        //列数为两列
	        int spanCount = 2;
	        mLayoutManager = new GridLayoutManager(MyApplication.getContext(),spanCount);
	      
//	        mRecyclerView.setHasFixedSize(true);
	        mRecyclerView.setLayoutManager(mLayoutManager);
	        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
	        	@Override
	        	public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
	        		
	        		
	        		
	        		super.onScrolled(recyclerView, dx, dy);
//	        		Log.d("111", ""+mLayoutManager.findLastVisibleItemPosition());
//	        		
	        		if (mLayoutManager.findLastVisibleItemPosition()>=(urls.size()-1)&&
	        				!recyclerView.canScrollVertically(1)) {
	        			
//	        			Toast.makeText(MyApplication.getContext(), "到底啦！", Toast.LENGTH_SHORT).show();	        			
//	        			mAdapter.notifyDataSetChanged();
					}
	                
	        	}
	        	@Override
	        	public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//	        		if(newState==RecyclerView.SCROLL_STATE_IDLE)
//	        		mLayoutManager.invalidateSpanAssignments();
	        		
	        		super.onScrollStateChanged(recyclerView, newState);
	        	}
			});


	        mAdapter = new GridAdapter(urls,this);
	        mRecyclerView.setAdapter(mAdapter);	        
	        mAdapter.setOnItemClickListener(new OnItemClickListener() {
				
				@Override
				public void onItemLongClick(View view, int position) {					
				}
				
				@Override
				public void onItemClick(View view, int position) {
					SecondActivity activity = (SecondActivity) getActivity();
					activity.showFragment("Fragment_flow",urls.get(position));
//					Toast.makeText(MyApplication.getContext(), ""+position, Toast.LENGTH_SHORT).show();
					
				}
			});
	}

	public void update(List<String> newUrls) {
		if (newUrls != null) {		
			urls.clear();
			for (String string : newUrls) {				
				urls.add(string);
			}
			new Handler().post(new Runnable() {
				@Override
				public void run() {
					mAdapter.notifyDataSetChanged();
				}

			});
		}
	}
		
}
