package com.example.q_kang.pmsystem.ui.activity.document;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.ui.fragment.document.DocuDraftsFragment;
import com.example.q_kang.pmsystem.ui.fragment.document.DocuReceiveFragment;
import com.example.q_kang.pmsystem.ui.fragment.document.DocuSendFragment;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.FilterView;
import com.fingdo.statelayout.StateLayout;
import com.github.clans.fab.FloatingActionButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DocumentActivity extends BaseActivity {


    @BindView(R.id.ib_document_back)
    ImageButton ic_back;
    @BindView(R.id.fab_document_add)
    FloatingActionButton fab_add;
    @BindView(R.id.rv_document)
    RecyclerView rv_document;
    @BindView(R.id.sl_document)
    StateLayout sl_document;
    @BindView(R.id.sfl_document)
    SmartRefreshLayout sfl_document;
    @BindView(R.id.fv_document)
    FilterView filterView;
    @BindView(R.id.vp_document)
    ViewPager viewPager;
    @BindView(R.id.mic_document)
    MagicIndicator magicIndicator;


    private List<String> titleList;
    private List<Fragment> fragmentList = new ArrayList<>();
    private PagerAdapter pagerAdapter;

    private DocuReceiveFragment receiveFragment;
    private DocuSendFragment sendFragment;
    private DocuDraftsFragment draftsFragment;


    @Override
    public int bindLayout() {
        return R.layout.activity_document;
    }

    @Override
    public void doBusiness(Context mContext) {

        initFacButton();

        //ViewPager设置
        initViewPager();


        //viewPager 标题及数据
        initMagicIndicator();

    }



    private void initFacButton() {
        fab_add.hide(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fab_add.show(true);
                fab_add.setShowAnimation(AnimationUtils.loadAnimation(DocumentActivity.this, R.anim.show_from_bottom));
                fab_add.setHideAnimation(AnimationUtils.loadAnimation(DocumentActivity.this, R.anim.hide_to_bottom));
            }
        }, 300);

    }


    @OnClick({R.id.ib_document_back, R.id.fab_document_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_document_back:
                finish();
                break;
            case R.id.fab_document_add:
                Intent intent = new Intent(this, CreateDocumentActivity.class);
                startActivity(intent);
                break;
        }
    }


    private void initViewPager() {
        fragmentList = new ArrayList<>(3);
        receiveFragment = new DocuReceiveFragment();
        fragmentList.add(receiveFragment);
        sendFragment = new DocuSendFragment();
        fragmentList.add(sendFragment);
        draftsFragment = new DocuDraftsFragment();
        fragmentList.add(draftsFragment);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragmentList, this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        viewPager.setCurrentItem(0);
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);
                        break;
                    case 2:
                        viewPager.setCurrentItem(2);
                        break;
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initMagicIndicator() {
        titleList = new ArrayList<>();
        titleList.add("已接收");
        titleList.add("已发送");
        titleList.add("草稿箱");


        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);//即标题平分屏幕宽度的模式
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titleList == null ? 0 : titleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(titleList.get(index));
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setNormalColor(Color.parseColor("#88000000"));
                simplePagerTitleView.setSelectedColor(Color.BLUE);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                        switch (index) {
                            case 0:
                                viewPager.setCurrentItem(0);
                                break;
                            case 1:
                                viewPager.setCurrentItem(1);
                                break;
                            case 2:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 4));
                indicator.setLineWidth(UIUtil.dip2px(context, 30));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(Color.BLUE);
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
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

}
