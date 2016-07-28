package com.example.dell_pc.googleplay;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();


    }

    private void initView() {


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_drawer_am);//设置导航栏图标
        toolbar.setLogo(R.mipmap.ic_launcher);//设置app logo
        toolbar.setTitle("谷歌商店");//设置主标题
//        toolbar.setSubtitle("Subtitle");//设置子标题

        toolbar.inflateMenu(R.menu.activity_tool_bar);//设置右上角的填充菜单

        //Toolbar点击事件监听
        toolbar.setOnMenuItemClickListener(new MyToolBarListener());


        NavigationView navigationView = (NavigationView) findViewById(R.id.nv_menu);
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());

        View headerView = navigationView.getHeaderView(0);
        ImageView photo =(ImageView) headerView.findViewById(R.id.iv_photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "头像", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class MyToolBarListener implements Toolbar.OnMenuItemClickListener{

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


    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {

            switch (item.getItemId()){
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

}