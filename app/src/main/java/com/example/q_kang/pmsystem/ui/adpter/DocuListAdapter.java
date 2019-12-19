package com.example.q_kang.pmsystem.ui.adpter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.dao.AdminDao;
import com.example.q_kang.pmsystem.modules.bean.bean.DocumentData;
import com.example.q_kang.pmsystem.modules.bean.bean.UserBean;
import com.example.q_kang.pmsystem.present.im.DocUpdatePresenter;
import com.example.q_kang.pmsystem.ui.activity.document.DocumentDetailActivity;
import com.example.q_kang.pmsystem.ui.view.Utils.EscapeUtils;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.GlideHelper;
import com.example.q_kang.pmsystem.view.DocUpdateView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class DocuListAdapter extends BaseQuickAdapter<DocumentData.DataBean, BaseViewHolder> implements DocUpdateView {
    private Context context;
    private List<DocumentData.DataBean> data;
    private int flag;

    private DocUpdatePresenter updatePresenter;


    public DocuListAdapter(Context context, @Nullable List<DocumentData.DataBean> data, int flag) {
        super(R.layout.document_list_layout, data);
        this.context = context;
        this.data = data;
        this.flag = flag;
        updatePresenter = new DocUpdatePresenter(this);
    }

    @Override
    protected void convert(BaseViewHolder helper, DocumentData.DataBean item) {
//        List<DocumentData.DataBean.SendReceiveBean> send_receive = item.getSend_receive();
//        String image = send_receive.get(0).getImage();
        CircleImageView image = helper.getView(R.id.civ_document_person);
        ImageView iv_dot = helper.getView(R.id.iv_documnet_dot);
        if (flag == 2) {   //发送
            iv_dot.setVisibility(View.GONE);
            UserBean user = AdminDao.getUser();
            GlideHelper.loadNet(image, Globle.PHOTO_URL + user.getImage(), R.mipmap.image_weibo_home_2);
            helper.setText(R.id.tv_document_person, user.getRealName());
        } else if (flag == 1) {   //接收
            GlideHelper.loadNet(image, Globle.PHOTO_URL + item.getImage(), R.mipmap.image_weibo_home_2);
            helper.setText(R.id.tv_document_person, item.getRealName());

            List<DocumentData.DataBean.SendReceiveBean> send_receive = item.getSend_receive();
            if (send_receive != null) {
                DocumentData.DataBean.SendReceiveBean sendReceiveBean = send_receive.get(0);
                boolean isRead = sendReceiveBean.isIsRead();
                if (isRead) {
                    iv_dot.setImageResource(R.drawable.ic_doc_read);
                } else {
                    iv_dot.setImageResource(R.drawable.ic_doc_unread);

                }
            }
        }


        helper.setText(R.id.tv_document_title, item.getName());
        String content = item.getContent();

        Log.i(TAG, "convert content: " + content);
        if (content.contains("<p>")){
            content = Html.fromHtml(content,null,null).toString();
        }
        Log.i(TAG, "convert content  contains(\"<p>\"): " + content);
        Spanned spanned = Html.fromHtml(EscapeUtils.unescape(content));
        helper.setText(R.id.tv_document_content, spanned);
        helper.setText(R.id.tv_document_time, item.getAddTime().split("T")[0]);

        helper.getView(R.id.rl_document_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag == 1) {
                    List<DocumentData.DataBean.SendReceiveBean> send_receive = item.getSend_receive();
                    if (send_receive != null) {
                        DocumentData.DataBean.SendReceiveBean sendReceiveBean = send_receive.get(0);
                        String sendReceive_id = sendReceiveBean.getId();
                        boolean isRead = sendReceiveBean.isIsRead();
                        if (isRead) {
                            iv_dot.setImageResource(R.drawable.ic_doc_read);
                        } else {
                            iv_dot.setImageResource(R.drawable.ic_doc_unread);

                            Log.i(TAG, "onClick   sendReceive_id: " + sendReceive_id);
                            Log.i(TAG, "onClick   docname: " + item.getName());
                            updatePresenter.updateDocState(sendReceive_id, true);

                        }
                    }

                }


                Intent intent = new Intent(context, DocumentDetailActivity.class);
                intent.putExtra("document", item);
                intent.putExtra("flag", flag);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public void setState(int state) {

    }

    @Override
    public void updateDocState(String string) {
        Log.i(TAG, "updateDocState: " + string);
        if ("更新成功!".equals(string)) {

        }

    }
}
