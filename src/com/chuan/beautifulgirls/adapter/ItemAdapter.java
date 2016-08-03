package com.chuan.beautifulgirls.adapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.chuan.beautifulgirls.R;
import com.chuan.beautifulgirls.model.SizeModel;
import com.chuan.beautifulgirls.utils.OnItemClickListener;

import android.graphics.Bitmap;

import android.support.v4.app.Fragment;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.FrameLayout;

import android.widget.ImageView;




public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
   
    private Fragment fragment;
    public List<String> urls = new ArrayList<String>();
    private Map<String, SizeModel> url_models = new HashMap<String, SizeModel>();
    private OnItemClickListener onItemClickListener;
    
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
    	this.onItemClickListener = onItemClickListener;
    }
    
    public ItemAdapter(List<String> urls,Fragment fragment) {
        this.urls = urls;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    	ViewHolder holder = new ViewHolder(LayoutInflater.from(fragment.getActivity()).inflate(R.layout.flow_item,
                viewGroup, false));
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
   

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
    	
//    	viewHolder.textView.setText(""+position);
    	
    	//回调实现监听点击
		if (onItemClickListener != null) {
			viewHolder.imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					onItemClickListener.onItemClick(v, position);

				}
			});
		}
    	//如果图片及所在布局没处理过大小，进行处理大小，否则取得保存的大小设置布局。
    	if (!url_models.containsKey(urls.get(position))) {
    		
//    		向图像的布局传入url信息，方便url与图片计算得到的宽高绑定。（由于imageview作为了glide的Target，tag被glide占用了，
//    		imageview。setTag会报错。）
    		FrameLayout framelayout = (FrameLayout)(viewHolder.imageView.getParent());
    		SizeModel sizeModel = new SizeModel();
   		 	sizeModel.setUrl(urls.get(position));
   		 	framelayout.setTag(sizeModel);
    		
    		//glide加载图片，并且做适屏处理。
    		Glide.with(fragment).load(urls.get(position)).    	
        	asBitmap().fitCenter().
        	skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESULT).
        	override(BitmapImageViewTarget.SIZE_ORIGINAL, BitmapImageViewTarget.SIZE_ORIGINAL).
        	placeholder(R.drawable.place).error(R.drawable.error).
        	into(new DriverViewTarget(viewHolder.imageView));
    		 
    		
		} else {
			FrameLayout framelayout = (FrameLayout)(viewHolder.imageView.getParent());
			
			ViewGroup.LayoutParams params = framelayout.getLayoutParams();
			SizeModel sizeModel = url_models.get(urls.get(position));
			params.width = sizeModel.getWidth();
			params.height = sizeModel.getHeight();
			framelayout.setLayoutParams(params);
		
			
			Glide.with(fragment).load(urls.get(position)).    	
        	asBitmap().fitCenter().
        	skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESULT).
        	override(sizeModel.getWidth(),sizeModel.getHeight()).
        	into(viewHolder.imageView);
		}
    	
    	
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {    
    	ImageView imageView ;
//    	TextView textView;
    	
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img1);
//            textView = (TextView) itemView.findViewById(R.id.text1);
            
        }
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
			float scale = resource.getWidth() / (viewWidth * 1.0f);
			int viewHeight = (int) (resource.getHeight() / scale);

			//设置图片控件的容器高度为缩放后图片的高度
			FrameLayout frameLayout = (FrameLayout) imageView.getParent();
			ViewGroup.LayoutParams params = frameLayout.getLayoutParams();
			params.height = viewHeight;
			frameLayout.setLayoutParams(params);
			
			//将图片缩放后的高度宽度与图片URL绑定保存至map，以便在RecyclerView回收复用view时复用高宽。
			SizeModel sizeModel = (SizeModel) frameLayout.getTag();
			sizeModel.setSize(viewWidth, viewHeight);
			url_models.put(sizeModel.getUrl(), sizeModel);

			super.onResourceReady(resource, glideAnimation);
        }
    }
    
	
}
