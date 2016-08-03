package com.chuan.beautifulgirls;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.chuan.beautifulgirls.fragment.Fragment_flow;
import com.chuan.beautifulgirls.fragment.Fragment_index;
import com.chuan.beautifulgirls.fragment.Fragment_viewpager;
import com.chuan.beautifulgirls.utils.MyApplication;


import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SecondActivity extends AppCompatActivity{
	//声明相关变量
	public Fragment_index fragment_index;
	public Fragment_flow fragment_flow;
	public Fragment_viewpager fragment_viewpager;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView LeftMenu;
    private List<String> items = new ArrayList<String>();
    private com.chuan.beautifulgirls.adapter.DrawerLayoutAdapter mAdapter;
   
  
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.finishAll();
        setContentView(R.layout.activity_second);
        findViews(); //获取控件
        initViews();
        
        manageFragment();
        
       MyApplication.addActivity(this);
       
    }
    
    private void initViews(){
    	 toolbar.setTitle("Toolbar");//设置Toolbar标题
         toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
         toolbar.setTitleTextAppearance(this, R.style.Theme_ToolBar_Base_Title);
         setSupportActionBar(toolbar);
         getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         //创建返回键，并实现打开关/闭监听
         mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,0, 0) {
             @Override
             public void onDrawerOpened(View drawerView) {
                 super.onDrawerOpened(drawerView);
                
             }
             @Override
             public void onDrawerClosed(View drawerView) {
                 super.onDrawerClosed(drawerView);
                
             }
         };
         mDrawerToggle.syncState();
         mDrawerLayout.addDrawerListener(mDrawerToggle);
         //设置菜单列表
         items.add("清理缓存");
         items.add("清理内存");
         items.add("保存图片");
         
         mAdapter = new com.chuan.beautifulgirls.adapter.DrawerLayoutAdapter(this, items);      
         LeftMenu.setAdapter(mAdapter);
         LeftMenu.setOnItemClickListener(new OnItemClickListener() {

 			@Override
 			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
 				Toast.makeText(getApplicationContext(), items.get(position), Toast.LENGTH_SHORT).show();
 				switch (position) {
				case 0:
					clearDiskMemory();					
					break;
				case 1:
					clearMemory();
					break;
				}
 				
 			}
 		});
    }
    private void findViews() {
        
        toolbar = (Toolbar) findViewById(R.id.tl_custom);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        LeftMenu = (ListView) findViewById(R.id.left_menu);
    }
    
    private void manageFragment(){
    	
    	FragmentManager parentFm = getSupportFragmentManager();
    	FragmentTransaction ft = parentFm.beginTransaction();
    	fragment_flow = new Fragment_flow(); 
    	fragment_index = new Fragment_index();
    	fragment_viewpager = new Fragment_viewpager();
    	
    
//    	ft.add(0, fragment_flow);
//    	ft.add(1, fragment_index);
//    	ft.add(2, fragment_viewpager);
    	ft.replace(R.id.fragment_view,fragment_index);
    	ft.addToBackStack(null);
    	ft.commit();
    	
    }
    public void showFragment(Fragment fragment,String srcUrl){
    	if (fragment!=null) {
    		
    	
    		FragmentManager parentFm = getSupportFragmentManager();
        	FragmentTransaction ft = parentFm.beginTransaction();        	
        	hideFragment(fragment_flow,ft);
        	hideFragment(fragment_index,ft);
        	hideFragment(fragment_viewpager,ft);
        	if(!fragment.isAdded()){
    			ft.add(R.id.fragment_view, fragment);
    		}else {    			
    			ft.show(fragment);
			}        	
        	ft.commit();
        	if(!TextUtils.isEmpty(srcUrl)){        		
    			updateFragment(fragment, srcUrl);
    		}
		}
    }
    private void hideFragment(Fragment fragment,FragmentTransaction ft){
    	if(fragment.isVisible()){
    		ft.hide(fragment);
    	}
    	
    }
    private void clearMemory(){
    	Glide.get(MyApplication.getContext()).clearMemory();
    }
    private void clearDiskMemory(){
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				Glide.get(MyApplication.getContext()).clearDiskCache();				
			}
		}).start();
    	
    }
    @Override
    public void onBackPressed() {
		if (fragment_index.isVisible()) {			
			MyApplication.showFinishDialog(this);
		}
		if (fragment_flow.isVisible()) {			
			showFragment(fragment_index,"");
		}
		if (fragment_viewpager.isVisible()) {			
			showFragment(fragment_flow,"");
		}
    }
    public void updateFragment(Fragment fragment,String srcUrl){
    	toolbar.setVisibility(View.GONE);
		if (fragment instanceof Fragment_index) {
			toolbar.setVisibility(View.VISIBLE);
		} else if (fragment instanceof Fragment_flow) {
			Fragment_flow thisFrag = (Fragment_flow) fragment;
			List<String> newUrls = MyApplication.getChildUrlList(srcUrl);
			thisFrag.update(newUrls);
	
		} else if (fragment instanceof Fragment_viewpager) {
			
		}
    }
    
}
