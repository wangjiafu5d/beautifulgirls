package com.chuan.beautifulgirls.fragment;

import java.util.ArrayList;
import java.util.List;

import com.chuan.beautifulgirls.R;
import com.chuan.beautifulgirls.SecondActivity;
import com.chuan.beautifulgirls.adapter.ItemAdapter;
import com.chuan.beautifulgirls.utils.OnItemClickListener;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentFlow extends Fragment{
	private RecyclerView mRecyclerView;
    public ItemAdapter mAdapter;
    private StaggeredGridLayoutManager  mLayoutManager;
    public List<String> urls = new ArrayList<String>();   
    private SwipeRefreshLayout swipeRefreshLayout;
    public int i;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view =inflater.inflate(R.layout.flow_layout, container, false);
			initView(view);
			return view;
		}
		
		private void initView(View view) {
	        mRecyclerView = (RecyclerView) view.findViewById(R.id.flow_recyclerView);
	        //列数为两列
	        int spanCount = 2;
	        mLayoutManager = new StaggeredGridLayoutManager(
	            spanCount, 
	            StaggeredGridLayoutManager.VERTICAL);
	        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
	        mRecyclerView.setLayoutManager(mLayoutManager);
	        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
	        	@Override
	        	public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
	        		super.onScrolled(recyclerView, dx, dy);
//	        		
	        		if (!recyclerView.canScrollVertically(1)) {
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


	        mAdapter = new ItemAdapter(urls,this);
	        mRecyclerView.setAdapter(mAdapter);	        
	        mAdapter.setOnItemClickListener(new OnItemClickListener() {
				
				@Override
				public void onItemLongClick(View view, int position) {					
				}
				
				@Override
				public void onItemClick(View view, int position) {
					SecondActivity activity = (SecondActivity) getActivity();
					activity.showFragment("FragmentViewPager",urls.get(position));
				}
			});
//	        tv = (TextView)findViewById(R.id.textView1);
	        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
	        //设置刷新时动画的颜色，可以设置4个
	        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
	        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue  
	        	    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()  
	        	      .getDisplayMetrics()));  
	        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
	            
	            @Override
	            public void onRefresh() {
//	                tv.setText("正在刷新");
	            	int random = (int) ((Math.random()*urls.size())/1);
	            	urls.set(0, mAdapter.urls.get(random));
	            	
	                new Handler().post(new Runnable() {
	                    
	                    @Override
	                    public void run() {
	                    	
//	                        tv.setText("刷新完成");
	                    	mAdapter.notifyDataSetChanged();
	                        swipeRefreshLayout.setRefreshing(false);
	                    }
	                });
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
					mRecyclerView.scrollToPosition(0);
					mAdapter.notifyDataSetChanged();

				}

			});
		}
	}
}
