package com.example.q_kang.pmsystem.ui.adpter.event;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.dao.AdminDao;
import com.example.q_kang.pmsystem.modules.bean.bean.event.EventComment;
import com.example.q_kang.pmsystem.modules.bean.bean.event.EventData;
import com.example.q_kang.pmsystem.present.im.EventDetailPresenter;
import com.example.q_kang.pmsystem.view.CreateEventView;
import com.jimmy.common.util.ToastUtils;

import java.util.List;


public class EventCommentAdapter extends BaseQuickAdapter<EventComment.DataBean, BaseViewHolder> {
    private Activity context;
    private List<EventComment.DataBean> data;

    private String eventId;


    public EventCommentAdapter(Activity context, @Nullable List<EventComment.DataBean> data, String eventId) {
        super(R.layout.comment_event_list, data);
        this.context = context;
        this.data = data;
        this.eventId = eventId;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void convert(BaseViewHolder helper, EventComment.DataBean item) {
        helper.setText(R.id.tv_comment_person, item.getAdduser());
        helper.setText(R.id.tv_ecomment_content, item.getContent());
        helper.setText(R.id.tv_comment_time, item.getAddTime());

        ImageButton ib_edit = helper.getView(R.id.ib_comment_edit);

        String addUserId = item.getAddUserId();
        final String userID = AdminDao.getUserID();
        if (addUserId.equals(userID)) {
            ib_edit.setVisibility(View.VISIBLE);
            ib_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new MaterialDialog.Builder(context)
                            .title("事件批示编辑！")
                            .inputType(
                                    InputType.TYPE_CLASS_TEXT
                                            | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                            | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                            .inputRange(1, 30)
                            .positiveText("完成")
                            .input("请输入批示意见", "", false, new MaterialDialog.InputCallback() {
                                        @Override
                                        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                            Log.i(TAG, "onInput: " + input);
                                            EventDetailPresenter eventPresenter = initPresenter();

                                            eventPresenter.createComment(eventId, input.toString(), userID, item.getId());


                                        }
                                    }
                            )
                            .show();

                }
            });
        } else {
            ib_edit.setVisibility(View.INVISIBLE);
        }


    }

    @NonNull
    private EventDetailPresenter initPresenter() {
        return new EventDetailPresenter(new CreateEventView() {
            @Override
            public void setState(int state) {

            }

            @Override
            public void createEvent(String string) {

            }

            @Override
            public void getEventDetail(EventData.DataBean dataBean) {

            }

            @Override
            public void getEventComment(String string) {

            }

            @Override
            public void postComment(String string) {
                Log.i(TAG, "postComment: " + string);
                if ("评论成功".equals(string)) {
                    ToastUtils.showToast(context, "批示修改成功~~~");
                    context.finish();
                }
            }
        });
    }


}