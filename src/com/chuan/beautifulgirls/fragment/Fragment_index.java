package com.chuan.beautifulgirls.fragment;

import java.util.ArrayList;
import java.util.List;

import com.chuan.beautifulgirls.R;
import com.chuan.beautifulgirls.SecondActivity;
import com.chuan.beautifulgirls.adapter.GridAdapter;

import com.chuan.beautifulgirls.utils.MyApplication;
import com.chuan.beautifulgirls.utils.OnItemClickListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Fragment_index extends Fragment{
	private RecyclerView mRecyclerView;
    public GridAdapter mAdapter;
    private GridLayoutManager  mLayoutManager;
   
    public List<String> urls = new ArrayList<String>();   
   
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view =inflater.inflate(R.layout.index_grid_layout, container, false);
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
	        		Log.d("111", ""+mLayoutManager.findLastVisibleItemPosition());
//	        		
	        		if (mLayoutManager.findLastVisibleItemPosition()>=(urls.size()-1)&&
	        				!recyclerView.canScrollVertically(1)) {
	        			
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


	        mAdapter = new GridAdapter(urls,this);
	        mRecyclerView.setAdapter(mAdapter);	        
	        mAdapter.setOnItemClickListener(new OnItemClickListener() {
				
				@Override
				public void onItemLongClick(View view, int position) {					
				}
				
				@Override
				public void onItemClick(View view, int position) {
					SecondActivity activity = (SecondActivity) getActivity();
					activity.showFragment(activity.fragment_flow);
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
			for (int i = 0; i < 100; i++) {
				urls.add("http://ww1.sinaimg.cn/mw1024/be447c45gw1f3ntze3ea6j20f00jzwgk.jpg");
			}
		}
}
