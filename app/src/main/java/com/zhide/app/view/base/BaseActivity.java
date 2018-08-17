package com.zhide.app.view.base;

import android.app.Activity;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.eventBus.DefaultEvent;
import com.zhide.app.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class BaseActivity extends AppCompatActivity implements DrawerLayout.DrawerListener {

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.tvTitleBar)
    View tvTitleBar;
    @BindView(R.id.rlBaseTitleLayout)
    RelativeLayout rlBaseTitleLayout;
    @BindView(R.id.ivGoBack)
    ImageView ivGoBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivNext)
    ImageView ivNext;
    @BindView(R.id.tvNext)
    TextView tvNext;
    @BindView(R.id.llCenterView)
    LinearLayout llCenterView;
    @BindView(R.id.llEmptyPage)
    LinearLayout llEmptyPage;
    @BindView(R.id.rlAddSelectContain)
    RelativeLayout rlAddSelectContain;
    private boolean needTranslucent = true;
    private static final int INVALID_VAL = -1;
    private int mTitleColor = 0;

    /**
     * 外部布局页面统一从这里传进来
     *
     * @return
     */
    protected abstract int getCenterView();

    /**
     * 初始化标题栏的内容，设置标题栏标题，icon，返回事件，等等，在各自子类实现
     */
    protected abstract void initHeader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getVew());
        // 全部绑定ButterKnife
        ButterKnife.bind(this);
        // 全局注册EventBus ，，EventBus貌似不能重复注册，这里判断一下
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        updateBaseData();

    }

    /**
     * 全局处理一些判断的时候可以用
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDefaultEventBus(DefaultEvent event) {

    }

    /**
     * 从子类获取中间部分页面后，初始化整个界面
     *
     * @return
     */
    private View getVew() {
        View mContainView = LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        LayoutInflater inflater = getLayoutInflater();
        if (getCenterView() != 0) {
            View mAddView = inflater.inflate(getCenterView(), null);
            LinearLayout llCenterView = mContainView.findViewById(R.id.llCenterView);
            llCenterView.addView(mAddView, new ViewGroup.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
        }
        return mContainView;
    }

    /**
     * 设置一些，初始化一些基类的事件，初始化等等
     */
    private void updateBaseData() {
        initHeader();
        translucentStatus(this, mTitleColor);
        drawerLayout.addDrawerListener(this);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 设置状态栏的颜色
     *
     * @param activity
     * @param statusColor
     */
    protected void translucentStatus(Activity activity, int statusColor) {
        if (!getIsNeedTranslucent()) {
            return;
        }
        int setColor;
        if (statusColor == 0) {
            setColor = activity.getResources().getColor(R.color.main_blue_color);
        } else {
            setColor = activity.getResources().getColor(statusColor);
        }
        //当前手机版本为5.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (statusColor != INVALID_VAL) {
                activity.getWindow().setStatusBarColor(setColor);
            }
            return;
        }
    }

    /**
     * 这是设置全局的状态栏颜色
     * 从子类传进状态栏的颜色，记得在子类重写的initHeader()方法里面设置，否则会失效。
     *
     * @param color
     */
    protected void setHeaderTitleBarColor(int color) {
        mTitleColor = color;
    }

    /**
     * 侧滑栏监听事件
     *
     * @param drawerView
     * @param slideOffset
     */
    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    /**
     * 侧滑栏监听事件
     *
     * @param drawerView
     */
    @Override
    public void onDrawerOpened(View drawerView) {

    }

    /**
     * 侧滑栏监听事件
     *
     * @param drawerView
     */
    @Override
    public void onDrawerClosed(View drawerView) {

    }

    /**
     * 侧滑栏监听事件
     *
     * @param newState
     */
    @Override
    public void onDrawerStateChanged(int newState) {

    }

    public boolean getIsNeedTranslucent() {
        return needTranslucent;
    }

    /**
     * 设置是否需要透明化状态栏，默认true，可以自己设置状态栏颜色，，
     *
     * @param needTranslucent
     */
    public void setIsNeedTranslucent(boolean needTranslucent) {
        this.needTranslucent = needTranslucent;
    }

    /**
     * 设置左上角返回按钮,默认显示
     *
     * @param visibility
     */
    protected void setLeftIconVisibility(int visibility) {
        if (ivGoBack == null) {
            return;
        }
        ivGoBack.setVisibility(visibility);
    }

    /**
     * 设置右边文字按钮状态，默认隐藏
     *
     * @param visibility
     */
    protected void setRightTextVisibility(int visibility) {
        if (tvNext == null) {
            return;
        }
        tvNext.setVisibility(visibility);
    }

    /**
     * 设置右边图标按钮状态 ，默认隐藏
     * @param visibility
     */
    protected void setRightIconVisibility(int visibility) {
        if (ivNext == null) {
            return;
        }
        ivNext.setVisibility(View.VISIBLE);
    }

    /**
     * 设置右边图标按钮的图标
     * @param resId
     */
    protected void setRightIcon(int resId) {
        if (ivNext != null) {
            ivNext.setImageResource(resId);
        }
    }
    /**
     * 设置右边按钮文字
     * @param rightText
     */
    protected void setHeader_RightText(String rightText) {
        if (tvNext == null) {
            return;
        }
        tvNext.setText(rightText);
    }

    /**
     * 设置标题栏标题
     * @param title
     */
    protected void setHeaderTitle(String title) {
        if (tvTitle == null || title == null) {
            return;
        }
        tvTitle.setText(title);
    }

    /**
     * 设置右边图标按钮的监听事件
     * @param listener
     */
    protected void setHeader_rightIconListener(View.OnClickListener listener) {
        if (ivNext != null) {
            ivNext.setOnClickListener(listener);
        }
    }

    /**
     * 设置左边返回按钮的监听事件，默认是直接返回，如需要添加其他附带操作就可以
     * 调用这个事件
     * @param listener
     */
    protected void setHeader_LeftClickListener(View.OnClickListener listener) {
        if (ivGoBack != null) {
            ivGoBack.setOnClickListener(listener);
        }
    }
    /**
     * 设置右边文字按钮的点击事件监听
     * @param listener
     */
    protected void setHeader_RightTextClickListener(View.OnClickListener listener) {
        if (tvNext != null) {
            tvNext.setOnClickListener(listener);
        }

    }



    /**
     * 解除EventBus注册
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
