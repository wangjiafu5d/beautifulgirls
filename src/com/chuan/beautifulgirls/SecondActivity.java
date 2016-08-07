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
	//������ر���
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
		findViews(); // ��ȡ�ؼ�
		initViews();// ��ʼ���ؼ�
		manageFragment();//��ʾĿ¼��Ƭ
		MyApplication.addActivity(this);
		checkUpdate();// ����Ƿ��ȡ��ͼƬURL��ַ�б�
	}
    
    private void initViews(){
    	 toolbar.setTitle(getString(R.string.title));//����Toolbar����
         toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //���ñ�����ɫ
         toolbar.setTitleTextAppearance(this, R.style.Theme_ToolBar_Base_Title);
         setSupportActionBar(toolbar);
         getSupportActionBar().setHomeButtonEnabled(true); //���÷��ؼ�����
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);//�����Ͻ�ͼ�����߼���һ�����ص�ͼ�� ��
         //�������ؼ�����ʵ�ִ�/�رռ���
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
         //���ò˵��б�
         menu = getResources().getStringArray(R.array.menu); 
         mAdapter = new DrawerLayoutAdapter(this, menu);      
         LeftMenu.setAdapter(mAdapter);
         //�໬�˵�����¼���������
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
    //���ݴ�����Ƭ������ʾ��ˢ����Ƭ
    public void showFragment(String fragment,String srcUrl){
		// toolbar.setVisibility(View.GONE);
		FragmentManager parentFm = getSupportFragmentManager();
		FragmentTransaction ft = parentFm.beginTransaction();
		//���ViewPager��Ƭ����������Ƭ�����������Ƴ��ɵģ��ټ����½���ҳ�棬����ʾ��
		if (fragmentViewPager != null && fragmentViewPager.isAdded()) {
			ft.remove(fragmentViewPager);
		}
		//Ŀ¼��Ƭҳ����ٲ�����ʾҳ�����ʾ������
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
			flow_keyUrl = srcUrl;//�������뵱ǰ�ٲ�����ʾҳ�����õļ�ֵURL,���ڲ˵������ˢ��ҳ��ʱʹ��
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
    //������һ��ҳ�棬�������ҳ�����Ƿ��˳�����Ի���
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
    //����URLˢ��ҳ��
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
    
    //ÿ��333ms���һ���Ƿ�����ͼƬURL��ַ,��������ˢ��ҳ��,6���û��õ������������ʾ
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
