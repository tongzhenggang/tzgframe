package com.frame.tzg;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import config.Urls;
import sample.TestActivity;
import tool.AppUpdateManager;

/** 
 * description: 侧滑框架首页
 * autour: tongzhenggang@126.com
 * date: 2017/9/14 
*/
public class SideslipActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_demo)
    Button tvDemo;
    @BindView(R.id.content_main)
    RelativeLayout contentMain;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sideslip);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Init();

    }

    private void Init() {
        /**
         * 侧边栏按钮
         */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

/*        //注册EventBus
        EventBus.getDefault().register(this);*/

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /**
         * 版本检查
         */
        AppUpdateManager manager = new AppUpdateManager(this, Urls.AppUpdata, false);
        // 检查软件更新
        manager.checkUpdate();

        Logger.d("检查软件更新");

        /**
         * test btn
         */
        tvDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), TestActivity.class));

            }
        });


        /**
         * 标签和别名同时设置
         */
        String alias = "tzg";
        Set<String> Tags = new HashSet<String>();
        Tags.add("杭州");
        Tags.add("上海");
        JPushInterface.setAliasAndTags(this, alias, Tags, new TagAliasCallback() {
            @Override
            public void gotResult(int arg0, String arg1, Set<String> arg2) {
                Logger.d("status:" + arg0);
                Logger.d("alias:" + arg1);
                Logger.d("tags:" + arg2.toString());
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //根据 Tag 取消请求
//        OkGo.getInstance().cancelTag(this);

        //取消所有请求
        OkGo.getInstance().cancelAll();

        //解除注册
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onPause() {
        JPushInterface.onPause(this);

        super.onPause();
    }

    @Override
    protected void onResume() {
        JPushInterface.onResume(this);
        super.onResume();
    }


   /* @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent){
        ToastUtil.show(getApplicationContext(),messageEvent.getMessage());

    }*/


}
