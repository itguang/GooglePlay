package com.example.dell_pc.googleplay;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import fragment.BaseFragment;
import fragment.FragmentFactory;

public class MainActivity extends BaseActivity {

    private NavigationView navigationView;//左抽屉
    private String[] tab_name; //tab 名称
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initToolBar();
        initView();
        initLeftDrawLayout();

    }

    /**
     * 初始化左抽屉布局
     */
    private void initLeftDrawLayout() {

        navigationView = (NavigationView) findViewById(R.id.nv_menu);
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());
        View headerView = navigationView.getHeaderView(0);//拿到头布局
        ImageView photo = (ImageView) headerView.findViewById(R.id.iv_photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "头像", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_drawer_am);//设置导航栏图标


        toolbar.setLogo(R.mipmap.ic_launcher);//设置app logo
        toolbar.setTitle("谷歌商店");//设置主标题
        toolbar.inflateMenu(R.menu.activity_tool_bar);//设置右上角的填充菜单
        //Toolbar点击事件监听
        toolbar.setOnMenuItemClickListener(new MyToolBarListener());
        /**
         * 导航栏图标 点击事件监听
         */
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.dl);
                drawerLayout.openDrawer(navigationView);//打开左抽屉
            }
        });

    }

    /**
     * 初始化主页面内容
     */
    public void initView() {
        viewPager = (ViewPager) findViewById(R.id.vp_pager);
        PagerTabStrip tabStrip = (PagerTabStrip) findViewById(R.id.pager_tab_strip);
        //设置标签下划线的颜色
        tabStrip.setTabIndicatorColor(getResources().getColor(R.color.indicatorcolor));
        //得到Tab标签内容
        tab_name = getResources().getStringArray(R.array.tab_names);

        MainAdapter myAdapter = new MainAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myAdapter);
        //当切换页面的时候重新请球服务器
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                BaseFragment fragment =FragmentFactory.createFragment(position);
                fragment.show();
            }
        });

    }

    /**
     * ToolBar 菜单点击事件
     */
    class MyToolBarListener implements Toolbar.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId()) {
                case R.id.action_item1:
                    Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                case R.id.action_item2:
                    Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();

            }

            return true;
        }
    }

    /**
     * 左抽屉点击事件监听
     */
    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {

            switch (item.getItemId()) {
                case R.id.nav_home:
                    Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();

                    //头布局无法响应 OnNavigationItemSelectedListener 事件
//                case R.id.iv_photo:
//                    Toast.makeText(MainActivity.this, "头像", Toast.LENGTH_SHORT).show();
                case R.id.sub_item1:
                    Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();

            }
            return true;
        }
    }

    /**
     * ViewPager 适配器
     */
    class MainAdapter extends FragmentStatePagerAdapter {

        public MainAdapter(FragmentManager fm) {
            super(fm);
        }

        //每个条目返回的Fragment
        @Override
        public Fragment getItem(int position) {


            return FragmentFactory.createFragment(position);
        }

        @Override
        public int getCount() {
            return tab_name.length;
        }

        //返回每个条目的标题
        @Override
        public CharSequence getPageTitle(int position) {

            return tab_name[position];
        }
    }

}
