package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhide.app.R;
import com.zhide.app.utils.ToastUtil;
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
 * @author Admin
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    @BindView(R.id.tvHomeTab)
    TextView tvFirstTab;
    @BindView(R.id.tvAboutTab)
    TextView tvSecondTab;
    @BindView(R.id.tvMineTab)
    TextView tvThirdTab;
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

    }

    @OnClick({R.id.tvHomeTab, R.id.tvAboutTab, R.id.tvMineTab, R.id.tvFourthTab})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvHomeTab:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tvAboutTab:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tvMineTab:
                viewPager.setCurrentItem(2);
                break;
            case R.id.tvFourthTab:

                break;
        }

    }
}
