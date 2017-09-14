package com.frame.tzg;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.tzg.fragment1.Fragment1;
import com.frame.tzg.fragment2.Fragment2;
import com.frame.tzg.fragment3.Fragment3;
import com.frame.tzg.fragment4.Fragment4;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import config.Urls;
import tool.AppUpdateManager;

/**
 * description: TAB 框架
 * autour: tongzhenggang@126.com
 * date: 2017/9/14
 */
public class MainFragmentActivity extends AppCompatActivity {

    @BindView(R.id.main_fragment_container)
    FrameLayout mainFragmentContainer;
    @BindView(R.id.maintv1)
    TextView maintv1;
    @BindView(R.id.main_tab1_tv)
    TextView mainTab1Tv;
    @BindView(R.id.main_tab1_ll)
    LinearLayout mainTab1Ll;
    @BindView(R.id.maintv2)
    TextView maintv2;
    @BindView(R.id.main_tab2_tv)
    TextView mainTab2Tv;
    @BindView(R.id.main_tab2_ll)
    LinearLayout mainTab2Ll;
    @BindView(R.id.maintv3)
    TextView maintv3;
    @BindView(R.id.main_tab3_tv)
    TextView mainTab3Tv;
    @BindView(R.id.main_tab3_ll)
    LinearLayout mainTab3Ll;
    @BindView(R.id.maintv4)
    TextView maintv4;
    @BindView(R.id.main_tab4_tv)
    TextView mainTab4Tv;
    @BindView(R.id.main_tab4_ll)
    LinearLayout mainTab4Ll;
    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main_fragment);
        ButterKnife.bind(this);

        Init();


    }

    private void Init() {
        fragmentManager = getSupportFragmentManager();
        initFragment();
        initTab();
        showFragment(0);
        initTabColor(0);

        /**
         * 自动检测版本升级
         */
        new AppUpdateManager(this, Urls.AppUpdata, false);
    }

    /**
     * 初始化fragment,根据实际的fragment的数量进行调整
     */
    public void initFragment() {
        //根据实际情况进行调整添加
        fragmentList.add(new Fragment1());
        fragmentList.add(new Fragment2());
        fragmentList.add(new Fragment3());
        fragmentList.add(new Fragment4());

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        for (Fragment fragment : fragmentList) {
            transaction.add(R.id.main_fragment_container, fragment);
            transaction.hide(fragment);
        }
        transaction.commit();
    }

    /**
     * 初始化导航栏各个tab 关联矢量图标文件 设置图表的大小
     */
    public void initTab() {
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "iconfont/iconfont.ttf");

        maintv1.setTypeface(iconfont);
        maintv2.setTypeface(iconfont);
        maintv3.setTypeface(iconfont);
        maintv4.setTypeface(iconfont);

        maintv1.setTextSize(25);
        maintv2.setTextSize(25);
        maintv3.setTextSize(25);
        maintv4.setTextSize(25);
    }

    /**
     * 指定某个tab 显示
     *
     * @param index
     */
    public void initTabColor(int index) {
        maintv1.setTextColor(getResources().getColor(R.color.tab_text_color));
        maintv2.setTextColor(getResources().getColor(R.color.tab_text_color));
        maintv3.setTextColor(getResources().getColor(R.color.tab_text_color));
        maintv4.setTextColor(getResources().getColor(R.color.tab_text_color));

        mainTab1Tv.setTextColor(getResources().getColor(R.color.tab_text_color));
        mainTab2Tv.setTextColor(getResources().getColor(R.color.tab_text_color));
        mainTab3Tv.setTextColor(getResources().getColor(R.color.tab_text_color));
        mainTab4Tv.setTextColor(getResources().getColor(R.color.tab_text_color));

        if (index == 0) {
            maintv1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            mainTab1Tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        if (index == 1) {
            maintv2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            mainTab2Tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        if (index == 2) {
            maintv3.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            mainTab3Tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        if (index == 3) {
            maintv4.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            mainTab4Tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    /***
     * 显示指定fragment
     *
     * @param index 指定fragment在集合中的index
     */
    public void showFragment(int index) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        for (Fragment fragment : fragmentList) {
            transaction.hide(fragment);
        }
        transaction.show(fragmentList.get(index));
        transaction.commit();
    }

    @OnClick({R.id.main_tab1_ll, R.id.main_tab2_ll, R.id.main_tab3_ll, R.id.main_tab4_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_tab1_ll:
                initTabColor(0);
                showFragment(0);
                break;
            case R.id.main_tab2_ll:
                initTabColor(1);
                showFragment(1);
                break;
            case R.id.main_tab3_ll:
                initTabColor(2);
                showFragment(2);
                break;
            case R.id.main_tab4_ll:
                initTabColor(3);
                showFragment(3);
                break;
        }
    }
}

