package com.chuan.beautifulgirls;


import java.util.List;
import com.bumptech.glide.Glide;
import com.chuan.beautifulgirls.adapter.DrawerLayoutAdapter;
import com.chuan.beautifulgirls.fragment.FragmentFlow;
import com.chuan.beautifulgirls.fragment.FragmentIndex;
import com.chuan.beautifulgirls.fragment.FragmentViewFager;
import com.chuan.beautifulgirls.utils.MyApplication;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SecondActivity extends AppCompatActivity{
	//声明相关变量
	public FragmentIndex fragmentIndex;
	public FragmentFlow fragmentFlow;
	public FragmentViewFager fragmentViewPager;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView LeftMenu;
    private String[] menu;
    private DrawerLayoutAdapter mAdapter;
    private String flow_keyUrl;
    private int i=0;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.finishAll();
		setContentView(R.layout.activity_second);
		findViews(); // 获取控件
		initViews();// 初始化控件
		manageFragment();//显示目录碎片
		MyApplication.addActivity(this);
		checkUpdate();// 检查是否获取到图片URL地址列表
	}
    
    private void initViews(){
    	 toolbar.setTitle(getString(R.string.title));//设置Toolbar标题
         toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
         toolbar.setTitleTextAppearance(this, R.style.Theme_ToolBar_Base_Title);
         setSupportActionBar(toolbar);
         getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);//给左上角图标的左边加上一个返回的图标 。
         //创建返回键，并实现打开/关闭监听
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
         menu = getResources().getStringArray(R.array.menu); 
         mAdapter = new DrawerLayoutAdapter(this, menu);      
         LeftMenu.setAdapter(mAdapter);
         //侧滑菜单点击事件监听处理
		LeftMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				switch (position) {
				case 0:
					clearDiskMemory();
					break;
				case 1:
					clearMemory();
					break;
				case 2:
					updateFragment();
					break;
				case 3:
					MyApplication.finishAll();
					break;
				}
				Toast.makeText(getApplicationContext(), getString(R.string.finish) + menu[position], Toast.LENGTH_SHORT)
						.show();
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
    	fragmentIndex = new FragmentIndex();
    	fragmentFlow = new FragmentFlow(); 
    	ft.replace(R.id.fragment_view,fragmentIndex);
    	ft.addToBackStack(null);
    	ft.commit();
    	
    }
    //根据传入碎片名称显示和刷新碎片
    public void showFragment(String fragment,String srcUrl){
		// toolbar.setVisibility(View.GONE);
		FragmentManager parentFm = getSupportFragmentManager();
		FragmentTransaction ft = parentFm.beginTransaction();
		//如果ViewPager碎片被加入了碎片管理器，先移除旧的，再加入新建的页面，并显示。
		if (fragmentViewPager != null && fragmentViewPager.isAdded()) {
			ft.remove(fragmentViewPager);
		}
		//目录碎片页面和瀑布流显示页面的显示和隐藏
		if (fragment.equals("FragmentIndex")) {
			hideFragment(fragmentFlow, ft);
			ft.show(fragmentIndex);
			// toolbar.setVisibility(View.VISIBLE);
		} else if (fragment.equals("FragmentFlow")) {
			hideFragment(fragmentIndex, ft);
			if (!fragmentFlow.isAdded()) {
				ft.add(R.id.fragment_view, fragmentFlow);
			} else {
				ft.show(fragmentFlow);
			}
			flow_keyUrl = srcUrl;//保存跳入当前瀑布流显示页面所用的键值URL,用于菜单栏点击刷新页面时使用
		} else if (fragment.equals("FragmentViewPager")) {
			hideFragment(fragmentFlow, ft);
			fragmentViewPager = new FragmentViewFager();
			fragmentViewPager.setUrls(MyApplication.getUrl_map2().get(srcUrl));
			ft.add(R.id.fragment_view, fragmentViewPager);
		}

		ft.commit();

		if (fragment.equals("FragmentFlow")) {
			List<String> newUrls = MyApplication.getFlowUrlList(srcUrl);
			if (newUrls != null && newUrls.size() > 0) {
				fragmentFlow.update(newUrls);
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
    //返回上一级页面，如果是首页弹出是否退出程序对话框
    @Override
    public void onBackPressed() {
		if (fragmentIndex!=null&&fragmentIndex.isVisible()) {			
			MyApplication.showFinishDialog(this);
		}
		if (fragmentFlow!=null&&fragmentFlow.isVisible()) {			
			showFragment("FragmentIndex","");
		}
		if (fragmentViewPager!=null&&fragmentViewPager.isVisible()) {			
			showFragment("FragmentFlow","");
		}
    }
    //根据URL刷新页面
    public void updateFragment(){
    	if (fragmentIndex!=null&&fragmentIndex.isVisible()) {
			List<String> newUrls = MyApplication.getUrl_list1();
			if (newUrls != null && newUrls.size() > 0) {
				fragmentIndex.update(newUrls);
			}
		}
		if (fragmentFlow!=null&&fragmentFlow.isVisible()) {
			List<String> newUrls = MyApplication.getFlowUrlList(flow_keyUrl);
			if (newUrls != null && newUrls.size() > 0) {
				fragmentFlow.update(newUrls);
			}			
		}		
    }
    
    //每隔333ms检查一次是否获得了图片URL地址,如果获得了刷新页面,6秒后没获得弹出检查网络提示
    public void checkUpdate(){
		new Thread(new Runnable() {

			@Override
			public void run() {

				while (!MyApplication.urlUpdateFlag && i < 18) {
					try {
						Thread.sleep(333);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					if (MyApplication.urlUpdateFlag) {
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								List<String> newUrls = MyApplication.getUrl_list1();
								fragmentIndex.update(newUrls);
								Toast.makeText(MyApplication.getContext(), getString(R.string.hint), Toast.LENGTH_LONG)
										.show();
							}
						});
						break;
					}
					i++;
				}

				if (i == 18) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(MyApplication.getContext(), getString(R.string.warning), Toast.LENGTH_LONG)
									.show();
						}
					});
				}

			}
		}).start();
	}
}
