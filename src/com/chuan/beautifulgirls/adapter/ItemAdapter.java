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
    	
    	//�ص�ʵ�ּ������
		if (onItemClickListener != null) {
			viewHolder.imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					onItemClickListener.onItemClick(v, position);

				}
			});
		}
    	//���ͼƬ�����ڲ���û�������С�����д����С������ȡ�ñ���Ĵ�С���ò��֡�
    	if (!url_models.containsKey(urls.get(position))) {
    		
//    		��ͼ��Ĳ��ִ���url��Ϣ������url��ͼƬ����õ��Ŀ�߰󶨡�������imageview��Ϊ��glide��Target��tag��glideռ���ˣ�
//    		imageview��setTag�ᱨ����
    		FrameLayout framelayout = (FrameLayout)(viewHolder.imageView.getParent());
    		SizeModel sizeModel = new SizeModel();
   		 	sizeModel.setUrl(urls.get(position));
   		 	framelayout.setTag(sizeModel);
    		
    		//glide����ͼƬ����������������
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
        	//��ȡͼƬ�ؼ���Ⱥ�ԴͼƬ��ȣ�����õ���ͼƬ���������ŵ��ؼ���Сʱ�ĸ߶�
			int viewWidth = imageView.getWidth();
			float scale = resource.getWidth() / (viewWidth * 1.0f);
			int viewHeight = (int) (resource.getHeight() / scale);

			//����ͼƬ�ؼ��������߶�Ϊ���ź�ͼƬ�ĸ߶�
			FrameLayout frameLayout = (FrameLayout) imageView.getParent();
			ViewGroup.LayoutParams params = frameLayout.getLayoutParams();
			params.height = viewHeight;
			frameLayout.setLayoutParams(params);
			
			//��ͼƬ���ź�ĸ߶ȿ����ͼƬURL�󶨱�����map���Ա���RecyclerView���ո���viewʱ���ø߿�
			SizeModel sizeModel = (SizeModel) frameLayout.getTag();
			sizeModel.setSize(viewWidth, viewHeight);
			url_models.put(sizeModel.getUrl(), sizeModel);

			super.onResourceReady(resource, glideAnimation);
        }
    }
    
	
}
