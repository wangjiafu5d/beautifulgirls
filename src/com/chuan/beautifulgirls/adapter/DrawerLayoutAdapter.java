package com.chuan.beautifulgirls.adapter;

import java.util.ArrayList;
import java.util.List;

import com.chuan.beautifulgirls.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DrawerLayoutAdapter extends BaseAdapter{
	private List<String> items = new ArrayList<String>();
	private LayoutInflater inflater;
	
	
	public DrawerLayoutAdapter(Context context,List<String> items) {
		this.items = items;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		
		return items.size();
	}

	@Override
	public String getItem(int position) {
		
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null ;
		if(convertView==null){
			convertView = inflater.inflate(R.layout.drawerlayout_item, parent, false);
			TextView item = (TextView) convertView.findViewById(R.id.dl_item);
			holder = new ViewHolder();
			holder.item = item;
			
			convertView.setTag(holder);			
		}else {			
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.item.setText(items.get(position));
		
		return convertView;
	}
	
	class ViewHolder {
		TextView item;
	}

}
