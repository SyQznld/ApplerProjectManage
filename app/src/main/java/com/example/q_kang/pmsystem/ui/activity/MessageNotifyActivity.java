package com.example.q_kang.pmsystem.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.dao.AdminDao;
import com.example.q_kang.pmsystem.modules.bean.bean.EventBusMessage;
import com.example.q_kang.pmsystem.modules.bean.bean.MessageNotifyData;
import com.example.q_kang.pmsystem.present.im.MessageListPresenter;
import com.example.q_kang.pmsystem.ui.adpter.MessageListAdapter;
import com.example.q_kang.pmsystem.view.MessageListView;
import com.fingdo.statelayout.StateLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageNotifyActivity extends BaseActivity implements MessageListView {

    @BindView(R.id.ib_message_back)
    ImageButton ibBack;
    @BindView(R.id.rv_message)
    RecyclerView rv_message;
    @BindView(R.id.iv_message_list_delete)
    ImageView iv_delete;
    //    @BindView(R.id.srl_message)
//    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.iv_message_list_search)
    ImageView iv_search;
    @BindView(R.id.et_message_search)
    EditText et_search;
    @BindView(R.id.tv_message_search)
    TextView tv_search;
    @BindView(R.id.rl_message_search)
    RelativeLayout rl_search;
    @BindView(R.id.sl_message)
    StateLayout sl_news;
    @BindView(R.id.srl_message_list)
    SmartRefreshLayout refreshLayout;

    private MessageListPresenter messageListPresenter;
    private MessageListAdapter messageListAdapter;
    private List<MessageNotifyData.DataBean> messagesList = new ArrayList<>();
    private int count;


    @Override
    public int bindLayout() {
        return R.layout.activity_message_notify;
    }

    @Override
    public void doBusiness(Context mContext) {

        EventBus.getDefault().register(this);

        messageListPresenter = new MessageListPresenter(this);
//        messageListPresenter.getMessageList(AdminDao.getUserID());


        rv_message.setLayoutManager(new LinearLayoutManager(mContext));
        messageListAdapter = new MessageListAdapter(MessageNotifyActivity.this, messagesList);
        rv_message.setAdapter(messageListAdapter);


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                messageListPresenter.getMessageList(AdminDao.getUserID());
                refreshLayout.finishRefresh();
            }
        });
//        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                refreshLayout.finishLoadMore();
//            }
//        });


    }


    @OnClick({R.id.ib_message_back, R.id.tv_message_search, R.id.iv_message_list_search, R.id.iv_message_list_delete})
    public void OnViewClick(View view) {
        switch (view.getId()) {
            case R.id.ib_message_back:
                if (et_search.getVisibility() == View.VISIBLE) {
                    et_search.setVisibility(View.GONE);
                    tv_search.setVisibility(View.GONE);
                    iv_search.setVisibility(View.VISIBLE);
//                    newsDataList.clear();
//                    newsListPresenter.getNewsList(page, Globle.PAGE_LIMIT, "");
                } else {
                    finish();
                }
                break;
            case R.id.iv_message_list_search:
                et_search.setText("");
                et_search.setTextColor(Color.parseColor("#616161"));
                YoYo.with(Techniques.SlideInRight)
                        .duration(200)
                        .playOn(rl_search);
                et_search.setVisibility(View.VISIBLE);
                tv_search.setVisibility(View.VISIBLE);
                iv_search.setVisibility(View.GONE);
                break;
            case R.id.tv_message_search:
//                newsDataList.clear();
//                newsListPresenter.getNewsList(page, Globle.PAGE_LIMIT, et_search.getText().toString());
                break;
            case R.id.iv_message_list_delete:
                new MaterialDialog.Builder(MessageNotifyActivity.this)
                        .title("删除已读消息！")
                        .content("确定删除全部已读消息！")
                        .positiveText("确定")
                        .negativeText("取消")
                        .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which == DialogAction.POSITIVE) {

                                    messageListPresenter.deleteAllMessage(AdminDao.getUserID(), "1");
                                    dialog.dismiss();

                                } else if (which == DialogAction.NEGATIVE) {
                                    dialog.dismiss();
                                }
                            }
                        }).show();
                break;

        }
    }

    @Override
    public void getMessageList(String string) {
        Log.i(TAG, "getMessageList: " + string);

        MessageNotifyData messageNotifyData = new Gson().fromJson(string, new TypeToken<MessageNotifyData>() {
        }.getType());

        count = messageNotifyData.getCount();

        if (count == 0 || "".equals(string)) {
            sl_news.showEmptyView("", R.drawable.ic_no_data);
            iv_delete.setVisibility(View.GONE);
        } else {
            messagesList.clear();
            sl_news.showContentView();
            List<MessageNotifyData.DataBean> data = messageNotifyData.getData();
            messagesList.addAll(data);
            messageListAdapter.notifyDataSetChanged();
           if (string.contains("true")){
               iv_delete.setVisibility(View.VISIBLE);
           }else {
               iv_delete.setVisibility(View.GONE);
           }
        }
    }


    /**
     * EventBus监听
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(EventBusMessage messageEvent) {
        Log.i(TAG, "onMoonEvent: " + messageEvent.getMessage());
        switch (messageEvent.getMessage()) {
            case "deleteMessage":
                messageListPresenter.getMessageList(AdminDao.getUserID());
                break;

        }
    }

    @Override
    public void deleteAllMessage(String string) {
        Log.i(TAG, "deleteAllMessage: " + string);
        try {
            JSONObject jsonObject = new JSONObject(string);
            String Result = jsonObject.getString("Result");
            String Message = jsonObject.getString("Message");


            if ("1".equals(Result)) {
                Toast.makeText(MessageNotifyActivity.this, "消息删除失败~", Toast.LENGTH_SHORT).show();
            } else if ("0".equals(Result)) {
                Toast.makeText(MessageNotifyActivity.this, "消息删除成功~", Toast.LENGTH_SHORT).show();
                messageListPresenter.getMessageList(AdminDao.getUserID());

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        messageListPresenter.getMessageList(AdminDao.getUserID());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
