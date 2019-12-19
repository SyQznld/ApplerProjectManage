package com.example.q_kang.pmsystem.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.q_kang.pmsystem.JGuang.ExampleUtil;
import com.example.q_kang.pmsystem.JGuang.LocalBroadcastManager;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.Application;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.dao.AdminDao;
import com.example.q_kang.pmsystem.modules.bean.bean.UserBean;
import com.example.q_kang.pmsystem.ui.activity.schedule.ScheduleActivity;
import com.example.q_kang.pmsystem.ui.fragment.FrameFragment;
import com.example.q_kang.pmsystem.ui.fragment.HomeFragment;
import com.example.q_kang.pmsystem.ui.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener, ViewPager.OnPageChangeListener {
    @BindView(R.id.title_main)
    RelativeLayout titleMain;
    @BindView(R.id.tv_main_title_name)
    TextView tv_main_title_name;
    @BindView(R.id.ib_main_title_search)
    ImageButton ib_main_title_search;
    @BindView(R.id.ib_main_title_schedule)
    ImageButton ib_schedule;
    private ViewPager mViewPager;

    private BottomNavigationBar btn_naviBar;
    //    BadgeItem标签的消息数量
    private BadgeItem mHomeNumberBadgeItem;
    private BadgeItem mMusicNumberBadgeItem;
    private PagerAdapter pagerAdapter;
    private List<Fragment> fragmentList;

    public static boolean isForeground = false;

    private Application application;

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void doBusiness(Context mContext) {

        application = (Application) getApplication();

  //      registerMessageReceiver();  // used for receive msg
//        initTagAndAlias();
        JPushInterface.getAllTags(this, 0);
        JPushInterface.getAlias(this, 0);

//        JpushNotifictionUtil.customPushNotification(this,1,R.layout.jiguang_tuisong,R.mipmap.icon_cy,R.mipmap.icon_cy);

        initView();
        initPager();
        initNavugationBar();
    }

    private void initTagAndAlias() {
        UserBean user = AdminDao.getUser();
        if (user != null) {
            String userid = user.getUserId().replace("-", "");
            String roleid = user.getRoleId().replace("-", "");
            String departmentid = user.getDepartmentId().replace("-", "");


            Log.i("aaa", "initgetUserId: " + user.getUserId());
            Log.i("aaa", "initgetRoleId: " + user.getRoleId());
            Log.i("aaa", "initgetDepartmentId: " + user.getDepartmentId());
            Log.i("aaa", "inituserid: " + userid);
            Log.i("aaa", "initroleid: " + roleid);
            Log.i("aaa", "initdepartmentid: " + departmentid);
            Set<String> inPutTags = ExampleUtil.getInPutTags(this, userid + "," + roleid);
            String inPutAlias = ExampleUtil.getInPutAlias(this, departmentid);
            Log.i("aaa", "initTagAndAlias: " + inPutTags);
            Log.i("aaa", "initTagAndAlias: " + inPutAlias);
            if (inPutTags != null) {
                JPushInterface.setTags(MainActivity.this, 0, inPutTags);
            }
            if (inPutAlias != null) {
                JPushInterface.setAlias(MainActivity.this, 0, inPutAlias);
            }
        }
    }

    private void initPager() {
        fragmentList = new ArrayList<>(3);
//        fragmentList = new ArrayList<>(4);
//        fragmentList.add(new MessageFragment());
        fragmentList.add(new HomeFragment());
        fragmentList.add(new FrameFragment());
        fragmentList.add(new MineFragment());
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragmentList, this);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
//        mViewPager.setOffscreenPageLimit(4);
//        mViewPager.setCurrentItem(1);
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(this);
    }

    private void initView() {
        mViewPager = findViewById(R.id.mViewPager);
        btn_naviBar = findViewById(R.id.btn_naviBar);
    }

    private void initNavugationBar() {
        mHomeNumberBadgeItem = new BadgeItem()
                .setBorderWidth(2)
                .setBackgroundColor(Color.RED)
                .setText("10+")
                .setHideOnSelect(false); // TODO 控制便签被点击时 消失 | 不消失
        mMusicNumberBadgeItem = new BadgeItem()
                .setBorderWidth(2)
                .setBackgroundColor(Color.RED)
                .setText("5+")
                .setHideOnSelect(true);
        btn_naviBar
                .setMode(BottomNavigationBar.MODE_FIXED);
//         TODO 设置背景色样式
//        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        btn_naviBar.setTabSelectedListener(this);
        btn_naviBar.setActiveColor("#3395fa") //选中按钮的颜色
                .setInActiveColor("#FFA7A7A7")//未选中按钮的颜色
                .setBarBackgroundColor("#FFFFFF");//导航栏背景色

        btn_naviBar
//                .addItem(new BottomNavigationItem(R.mipmap.bottom_message, "消息"))
                .addItem(new BottomNavigationItem(R.mipmap.bottom_work, "办公"))
                .addItem(new BottomNavigationItem(R.mipmap.bottom_organizaton, "组织"))
                .addItem(new BottomNavigationItem(R.mipmap.bottom_mine, "关于"))
//                .setFirstSelectedPosition(1)
                .setFirstSelectedPosition(0)
                .initialise();

    }



    //导航栏方法1
    @Override
    public void onTabSelected(int position) {
        switch (position) {
//            case 0:
//                mViewPager.setCurrentItem(0);
//                tv_main_title_name.setText(R.string.message_title);
//                break;
//            case 1:
//                mViewPager.setCurrentItem(1);
//                tv_main_title_name.setText(R.string.app_name);
//                break;
//            case 2:
//                mViewPager.setCurrentItem(2);
//                tv_main_title_name.setText(R.string.frame_title);
//                break;
//            case 3:
//                mViewPager.setCurrentItem(3);
//                tv_main_title_name.setText(R.string.mine_title);
//                break;


            case 0:
                mViewPager.setCurrentItem(0);
                tv_main_title_name.setText(R.string.app_name);
                break;
            case 1:
                mViewPager.setCurrentItem(1);
                tv_main_title_name.setText(R.string.frame_title);
                break;
            case 2:
                mViewPager.setCurrentItem(2);
                tv_main_title_name.setText(R.string.mine_title);
                break;
            default:
                break;
        }
    }

    //导航栏方法2
    @Override
    public void onTabUnselected(int position) {

    }

    //导航栏方法3
    @Override
    public void onTabReselected(int position) {

    }

    //ViewPager方法1
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    //ViewPager方法2
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //ViewPager方法3
    @Override
    public void onPageSelected(int position) {
        switch (position) {
//            case 0:
//                btn_naviBar.selectTab(0);
//                break;
//            case 1:
//                btn_naviBar.selectTab(1);
//                break;
//            case 2:
//                btn_naviBar.selectTab(2);
//                break;
//            case 3:
//                btn_naviBar.selectTab(3);
//                break;
//

            case 0:
                btn_naviBar.selectTab(0);
                break;
            case 1:
                btn_naviBar.selectTab(1);
                break;
            case 2:
                btn_naviBar.selectTab(2);
                break;
        }
    }

    //ViewPager方法4
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick({R.id.tv_main_title_name, R.id.ib_main_title_search,R.id.ib_main_title_schedule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_main_title_name:
                break;
            case R.id.ib_main_title_search:
                Intent searchIntent = new Intent(this, SearchActivity.class);
                startActivity(searchIntent);
                break;
            case R.id.ib_main_title_schedule:
                Intent scheduleIntent = new Intent(this, ScheduleActivity.class);
                startActivity(scheduleIntent);
                break;
        }
    }

    private class PagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragList;
        private Context context;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public PagerAdapter(FragmentManager fm, List<Fragment> fragList, Context context) {
            super(fm);
            this.fragList = fragList;
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            return fragList.get(position);
        }

        @Override
        public int getCount() {
            return fragList.size();
        }
    }

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.q_kang.pmsystem.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    Toast.makeText(context, showMsg.toString(), Toast.LENGTH_LONG).show();
//                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e) {
            }
        }
    }

    // 定义一个变量，来标识是否退出
    private static boolean isExist;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {//按键是返回键
            if (!isExist) {//isExist为false,第一次按返回键
                isExist = true;
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //延迟3秒，变更isExist=false
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExist = false;
                    }
                }, 2000);
            } else {//isExist为true,第二次按返回键
                application.exitApp();
            }
        }
        return false;
    }


    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
}
