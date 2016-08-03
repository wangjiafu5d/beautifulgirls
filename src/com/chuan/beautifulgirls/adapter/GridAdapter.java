package com.chuan.beautifulgirls.adapter;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.chuan.beautifulgirls.R;

import com.chuan.beautifulgirls.utils.OnItemClickListener;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder>{
	private Fragment fragment;
    public List<String> urls = new ArrayList<String>();
    private OnItemClickListener onItemClickListener;
    
    public GridAdapter(List<String> urls, Fragment fragment) {    
    	this.urls = urls;
        this.fragment = fragment;
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener){
    	this.onItemClickListener = onItemClickListener;
	}
	
	class ViewHolder extends RecyclerView.ViewHolder{
		private ImageView imageView ;
		private TextView textView;
		public ViewHolder(View itemView) {
			super(itemView);
			imageView = (ImageView) itemView.findViewById(R.id.grid_img1);
			textView = (TextView) itemView.findViewById(R.id.grid_text1);
		}

	}

	@Override
	public int getItemCount() {		
		return urls.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, final int position) {
		// 回调实现监听点击
		viewHolder.textView.setText(""+position);
		if (onItemClickListener != null) {
			viewHolder.imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					onItemClickListener.onItemClick(v, position);

				}
			});
		}
		viewHolder.imageView.getMeasuredWidth();
//		FrameLayout framelayout = (FrameLayout)(viewHolder.imageView.getParent());
//		ViewGroup.LayoutParams params = framelayout.getLayoutParams();	
//		params.height = viewHolder.imageView.getWidth();
//		framelayout.setLayoutParams(params);
//		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//		  int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//		  viewHolder.imageView.measure(w, h);
		
		Glide.with(fragment).load(urls.get(position)).    	
    	asBitmap().
    	skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESULT). 
    	placeholder(R.drawable.place).error(R.drawable.error).
    	override(BitmapImageViewTarget.SIZE_ORIGINAL, BitmapImageViewTarget.SIZE_ORIGINAL).
    	into(new DriverViewTarget(viewHolder.imageView));
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		ViewHolder holder = new ViewHolder(LayoutInflater.from(fragment.getActivity()).inflate(R.layout.grid_item,
                viewGroup, false));
        return holder;
	}
	
	private class DriverViewTarget extends BitmapImageViewTarget {
    	private ImageView imageView;    	
    	
        public DriverViewTarget(ImageView view) {        	
			super(view);
			imageView = view;
            
        }

        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
        	//获取图片控件宽度和源图片宽度，计算得到将图片按比例缩放到控件大小时的高度
			int viewWidth = imageView.getWidth();
			android.view.ViewGroup.LayoutParams ivParams = imageView.getLayoutParams();
			ivParams.height = viewWidth;
			imageView.setLayoutParams(ivParams);
			
			
			//设置图片控件的容器高度为缩放后图片的高度
			LinearLayout linearLayout = (LinearLayout) imageView.getParent();
			ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
			params.height = viewWidth+linearLayout.findViewById(R.id.grid_text1).getHeight();
			linearLayout.setLayoutParams(params);

			super.onResourceReady(resource, glideAnimation);
        }
	}
	
}
