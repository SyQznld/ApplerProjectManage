package com.example.q_kang.pmsystem.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.alexvasilkov.gestures.commons.RecyclePagerAdapter;
import com.alexvasilkov.gestures.transition.GestureTransitions;
import com.alexvasilkov.gestures.transition.ViewsTransitionAnimator;
import com.alexvasilkov.gestures.transition.tracker.SimpleTracker;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.base.SettingsMenu;
import com.example.q_kang.pmsystem.modules.bean.bean.ChatData;
import com.example.q_kang.pmsystem.ui.adpter.ChatAdapter;
import com.example.q_kang.pmsystem.ui.adpter.PagerAdapter;
import com.example.q_kang.pmsystem.ui.fragment.TextFragment;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.Painting;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity {

    @BindView(R.id.line)
    FrameLayout line;
    @BindView(R.id.ib_record)
    ImageButton ibRecord;
    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.re_chat)
    RecyclerView reChat;
    @BindView(R.id.recycler_pager)
    ViewPager recyclerPager;
    @BindView(R.id.recycler_full_background)
    View recyclerFullBackground;
    @BindView(R.id.ib_chat_back)
    ImageButton ib_back;
    private FragmentManager mFragmentManager;
    private TextFragment mTextFragmentOne;
    private PagerAdapter pagerAdapter;
    private final SettingsMenu settingsMenu = new SettingsMenu();
    private ViewsTransitionAnimator<Integer> animator;

    @Override
    public int bindLayout() {
        return R.layout.activity_chat;
    }

    @Override
    public void doBusiness(Context mContext) {

        etInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                line.setVisibility(View.GONE);
            }
        });

        reChat.setLayoutManager(new LinearLayoutManager(mContext));
        ChatData data = new ChatData();
        data.setData("");
        data.setType(0);


        ChatData data1 = new ChatData();
        data1.setData("");
        data1.setType(1);

        ChatData data2 = new ChatData();
        data2.setData("");
        data2.setType(2);

        ChatData data3 = new ChatData();
        data3.setData("");
        data3.setType(1);

        List<ChatData> datas = new ArrayList<>();
        datas.add(data);
        datas.add(data1);
        datas.add(data2);
        datas.add(data3);

        final ChatAdapter chatAdapter = new ChatAdapter(ChatActivity.this, datas);
        reChat.setAdapter(chatAdapter);
//        reChat.scrollToPosition(datas.size()-1);
        reChat.smoothScrollToPosition(reChat.getBottom());

        chatAdapter.setOnItemClickListener(position -> {
            switch (position) {
                case 0:
//                    startActivity(CreateProjectActivity.class);
                    break;
                case 1:
                    animator.enter(0, true);
                    break;
                case 2:
                    break;
                case 3:
                    animator.enter(1, true);
                    break;
            }
        });
        final Painting[] paintings = Painting.list(getResources());

        pagerAdapter = new PagerAdapter(recyclerPager, paintings, settingsMenu);
        recyclerPager.setAdapter(pagerAdapter);
        recyclerPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.view_pager_margin));

        final SimpleTracker listTracker = new SimpleTracker() {
            @Override
            public View getViewAt(int position) {
//                if (position == 1 || position == 3) {
//                    RecyclerView.ViewHolder holder = reChat.findViewHolderForLayoutPosition(position);
//                    return holder == null ? null : ChatAdapter.getImageView(holder);
//                } else {
//                    return null;
//                }
                RecyclerView.ViewHolder holder = reChat.findViewHolderForLayoutPosition(position);

                boolean b = holder instanceof ChatAdapter.ImagerViewHolder;
                Log.i(TAG, "getViewAt: " + b);

                return null;
            }
        };

        final SimpleTracker pagerTracker = new SimpleTracker() {
            @Override
            public View getViewAt(int position) {
                RecyclePagerAdapter.ViewHolder holder = pagerAdapter.getViewHolder(position);
                return holder == null ? null : PagerAdapter.getImageView(holder);
            }
        };

        animator = GestureTransitions.from(reChat, listTracker).into(recyclerPager, pagerTracker);
        animator.addPositionUpdateListener((pos, isLeaving) -> applyImageAnimationState(pos));

    }


    private void applyImageAnimationState(float position) {
        recyclerFullBackground.setVisibility(position == 0f ? View.INVISIBLE : View.VISIBLE);
        recyclerFullBackground.setAlpha(position);
    }

    @OnClick({R.id.ib_chat_back, R.id.ib_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_chat_back:
                finish();
                break;
            case R.id.ib_record:
                line.setVisibility(View.VISIBLE);

                addFragment();
                break;
        }


    }

    @Override
    public void onBackPressed() {
        // We should leave full image mode instead of closing the screen
        if (!animator.isLeaving()) {
            animator.exit(true);
        } else {
            super.onBackPressed();
        }
    }

    private void addFragment() {
        if (null == mFragmentManager) {
            mFragmentManager = getSupportFragmentManager();
        }

        mTextFragmentOne = new TextFragment();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
//        fragmentTransaction.setCustomAnimations(
//                R.anim.push_left_in,
//                R.anim.push_left_out,
//                R.anim.push_left_in,
//                R.anim.push_left_out);

        fragmentTransaction.add(R.id.line, mTextFragmentOne);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

}
