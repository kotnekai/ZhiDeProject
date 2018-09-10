package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.utils.ResourceUtils;
import com.zhide.app.utils.UIUtils;
import com.zhide.app.view.adapter.FragmentAdapter;
import com.zhide.app.view.base.BaseActivity;
import com.zhide.app.view.fragment.HomeFragment;
import com.zhide.app.view.fragment.AboutFragment;
import com.zhide.app.view.fragment.MineFragment;
import com.zhide.app.view.views.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create by Admin on 2018/08/14
 *
 * @author Admin
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    @BindView(R.id.ivHomeTab)
    ImageView ivHomeTab;
    @BindView(R.id.ivAboutTab)
    ImageView ivAboutTab;
    @BindView(R.id.ivMineTab)
    ImageView ivMineTab;
    @BindView(R.id.tvFourthTab)
    TextView tvFourthTab;
    private List<Fragment> fragmentList;
    private FragmentAdapter adapter;

    @Override
    protected int getCenterView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.app_name));
        setLeftIconVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new AboutFragment());
        fragmentList.add(new MineFragment());
        tvFourthTab.setVisibility(View.GONE);//隐藏一个
        adapter = new FragmentAdapter(fragmentList, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        ivHomeTab.setSelected(true);

    }


    @OnClick({R.id.ivHomeTab, R.id.ivAboutTab, R.id.ivMineTab, R.id.tvFourthTab})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivHomeTab:
                viewPager.setCurrentItem(0);
                ivHomeTab.setSelected(true);
                ivAboutTab.setSelected(false);
                ivMineTab.setSelected(false);
                setHeaderTitle(getString(R.string.app_name));

                break;
            case R.id.ivAboutTab:
                viewPager.setCurrentItem(1);
                ivHomeTab.setSelected(false);
                ivAboutTab.setSelected(true);
                ivMineTab.setSelected(false);
                setHeaderTitle(getString(R.string.about_tab));

                break;
            case R.id.ivMineTab:
                viewPager.setCurrentItem(2);
                ivHomeTab.setSelected(false);
                ivAboutTab.setSelected(false);
                ivMineTab.setSelected(true);
                setHeaderTitle(getString(R.string.mine_tab_title));

                break;
            case R.id.tvFourthTab:

                break;
        }

    }
}
