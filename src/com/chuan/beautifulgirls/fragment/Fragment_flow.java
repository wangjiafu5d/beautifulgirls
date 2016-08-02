package com.chuan.beautifulgirls.fragment;

import java.util.ArrayList;
import java.util.List;

import com.chuan.beautifulgirls.R;
import com.chuan.beautifulgirls.SecondActivity;
import com.chuan.beautifulgirls.adapter.ItemAdapter;
import com.chuan.beautifulgirls.utils.MyApplication;
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
import android.widget.Toast;

public class Fragment_flow extends Fragment{
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
//	        mRecyclerView.setHasFixedSize(true);
	        mRecyclerView.setLayoutManager(mLayoutManager);
	        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
	        	@Override
	        	public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
	        		
	        		
	        		
	        		super.onScrolled(recyclerView, dx, dy);
//	        		
	        		if (!recyclerView.canScrollVertically(1)) {
	        			Toast.makeText(MyApplication.getContext(), "到底啦！", Toast.LENGTH_SHORT).show();	        			
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
					activity.showFragment(activity.fragment_viewpager);
					Toast.makeText(MyApplication.getContext(), ""+position, Toast.LENGTH_SHORT).show();
					
				}
			});
	       
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
	            	int random = (int) ((Math.random()*15)/1);
	            	mAdapter.urls.set(0, mAdapter.urls.get(random));
	            	
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
}
