package com.example.q_kang.pmsystem.ui.activity.document;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Layout;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.q_kang.pmsystem.Globle.Globle;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.dao.AdminDao;
import com.example.q_kang.pmsystem.modules.bean.bean.ChooseData;
import com.example.q_kang.pmsystem.modules.bean.bean.DocumentData;
import com.example.q_kang.pmsystem.modules.bean.bean.FileBean;
import com.example.q_kang.pmsystem.modules.bean.bean.MessageNotifyData;
import com.example.q_kang.pmsystem.modules.bean.bean.RecordBean;
import com.example.q_kang.pmsystem.present.im.DocumentPresenter;
import com.example.q_kang.pmsystem.ui.activity.ChooseActivity;
import com.example.q_kang.pmsystem.ui.activity.photo.ImageShowActivity;
import com.example.q_kang.pmsystem.ui.adpter.EventRecordAdapter;
import com.example.q_kang.pmsystem.ui.adpter.event.EveGatherFileAdapter;
import com.example.q_kang.pmsystem.ui.adpter.event.EveGatherPhotoAdapter;
import com.example.q_kang.pmsystem.ui.adpter.event.EventAssignAdapter;
import com.example.q_kang.pmsystem.ui.view.Utils.CommonUtils;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.GlideHelper;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.MyListView;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.PileLayout;
import com.example.q_kang.pmsystem.view.DocumentView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jimmy.common.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class DocumentDetailActivity extends BaseActivity implements DocumentView {


    @BindView(R.id.ib_document_detail_back)
    ImageButton ib_back;
    @BindView(R.id.ib_document_detail_transmit)
    ImageButton ib_transmit;
    @BindView(R.id.tv_document_detail_time)
    TextView tv_time;
    @BindView(R.id.civ_document_detail_image)
    CircleImageView civ_sendPerson;
    @BindView(R.id.tv_document_detail_sender)
    TextView tv_sendPerson;
    @BindView(R.id.tv_document_detail_title)
    TextView tv_name;
    @BindView(R.id.tv_document_detail_content)
    TextView tv_content;
    @BindView(R.id.rv_document_detail_photo)
    RecyclerView rv_photo;
    @BindView(R.id.lv_document_detail_record)
    MyListView lv_record;
    @BindView(R.id.rv_document_detail_file)
    RecyclerView rv_file;
    @BindView(R.id.pl_document_detail_report)
    PileLayout pl_report;
    @BindView(R.id.ib_document_detail_more)
    ImageButton ib_more;
    @BindView(R.id.ll_report)
    LinearLayout ll_report;
    @BindView(R.id.fam_document_detail_create)
    FloatingActionMenu fam_create;
    @BindView(R.id.fab_document_detail_save)
    FloatingActionButton fab_save;
    @BindView(R.id.fab_document_detail_report)
    FloatingActionButton fab_report;
    @BindView(R.id.ib_document_detail_receiver)
    ImageButton ib_receiver;
    @BindView(R.id.ib_document_detail_edit)
    ImageButton ib_edit;


    private ArrayList<ChooseData> memberList;
    private DocumentData.DataBean documentData;
    private int flag;

    private List<String> photos = new ArrayList<>();
    private EveGatherPhotoAdapter photoAdapter;

    private List<RecordBean> records = new ArrayList<>();
    private EventRecordAdapter recordAdapter;

    private List<FileBean> files = new ArrayList<>();
    private EveGatherFileAdapter fileAdapter;

    private DocumentPresenter documentPresenter;

    private String title, content, reportPerson, fileUpLoad;
    private List<String> assignPersonList = new ArrayList<>();


    private CircleImageView imageView;
    private LayoutInflater inflater;

    private List<String> indexList = new ArrayList<>();

    private List<String> receiverList = new ArrayList<>();
    private List<DocumentData.DataBean.SendReceiveBean> receiveBeanList;

    private MessageNotifyData.DataBean messageNotifyData;

    private List<String> docImageList = new ArrayList<>();


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    CharSequence charSequence = (CharSequence) msg.obj;
                    Log.i(TAG, "handleMessage: " + charSequence);
                    if (charSequence != null) {
                        tv_content.setText(charSequence);
                        tv_content.setMovementMethod(LinkMovementMethod.getInstance());
                        if (docImageList != null && docImageList.size() > 0) {
                            tv_content.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    int action = event.getAction();

                                    TextView tv = (TextView) v;
                                    CharSequence text = tv.getText();
                                    if (text instanceof SpannableString) {
                                        if (action == MotionEvent.ACTION_UP) {
                                            int x = (int) event.getX();
                                            int y = (int) event.getY();

                                            x -= tv.getTotalPaddingLeft();
                                            y -= tv.getTotalPaddingTop();

                                            x += tv.getScrollX();
                                            y += tv.getScrollY();

                                            Layout layout = tv.getLayout();
                                            int line = layout.getLineForVertical(y);
                                            int off = layout.getOffsetForHorizontal(line, x);

                                            ClickableSpan[] link = ((SpannableString) text).getSpans(off, off, ClickableSpan.class);
                                            if (link.length != 0) {
                                                link[0].onClick(tv);
                                            } else {
                                                Intent intent = new Intent(DocumentDetailActivity.this, ImageShowActivity.class);
                                                intent.putExtra("flag", "string");
                                                intent.putStringArrayListExtra("data", (ArrayList<String>) docImageList);
                                                startActivity(intent);
                                            }
                                        }
                                    }

                                    return true;
                                }

                            });
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public int bindLayout() {
        return R.layout.activity_document_detail;
    }

    @Override
    public void doBusiness(Context mContext) {


        inflater = LayoutInflater.from(mContext);
        documentPresenter = new DocumentPresenter(this);

        documentData = getIntent().getParcelableExtra("document");
        Log.i(TAG, "doBusiness  documentData: " + documentData);
        messageNotifyData = getIntent().getParcelableExtra("messagenotify");
        flag = getIntent().getIntExtra("flag", 0);

        Log.i(TAG, "getDocumentDetail flag: " + flag);

//        if (flag == 10) {
//            documentPresenter.getDocumentDetail(messageNotifyData.getId());
//        }
//        else {
//            documentPresenter.getDocumentDetail(documentData.getId());
//        }

        if (documentData != null) {
            initDocDetail(mContext);
        }


    }

    private void initDocDetail(Context mContext) {
        String realName = documentData.getRealName();
        String currUserName = AdminDao.getUser().getRealName();
        List<DocumentData.DataBean.SendReceiveBean> send_receive = documentData.getSend_receive();

        if (realName.equals(currUserName)) {
            if (send_receive.size() == 1) {
                if ("null".equals(send_receive.get(0).getRealName()) || send_receive.get(0).getRealName() == null) {
                    flag = 1;
                } else {
                    flag = 2;
                }
            } else {
                flag = 2;
            }
        } else {
            flag = 1;
        }
        Log.i(TAG, "initDocDetail: " + realName);
        if (flag == 1) { //接收
            GlideHelper.loadNet(civ_sendPerson, Globle.PHOTO_URL + documentData.getImage(), R.mipmap.image_weibo_home_2);
            tv_sendPerson.setText(realName);
            ib_receiver.setVisibility(View.GONE);
        } else if (flag == 2) { //发送   自己   发送给谁
            GlideHelper.loadNet(civ_sendPerson, Globle.PHOTO_URL + AdminDao.getUser().getImage(), R.mipmap.image_weibo_home_2);
            tv_sendPerson.setText(currUserName);
            ib_receiver.setVisibility(View.VISIBLE);
        }
        title = documentData.getName();
        tv_name.setText(title.trim());
        content = documentData.getContent();
        //<p>邮件邮件邮件<img src="/Scripts/ueditor/net/upload/image/20181121/6367840894837395569033739.png" title="3c32c8c35910f7d1b378c79e5fc7011.png" alt="3c32c8c35910f7d1b378c79e5fc7011.png"/></p>
        Log.i(TAG, "initDocDetail content: " + content);

        docImageList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Html.ImageGetter imageGetter = new Html.ImageGetter() {

                    @Override
                    public Drawable getDrawable(String source) {
                        docImageList.add(Globle.PHOTO_URL + source);

                        Drawable drawable;
                        drawable = CommonUtils.getImageNetwork(source);

                        if (drawable != null) {
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        } else if (drawable == null) {
                            return null;
                        }
                        return drawable;
                    }
                };
                content = content.replace("\n", "<br />");

                CharSequence charSequence = Html.fromHtml(content, imageGetter, null);
                Message ms = Message.obtain();
                ms.what = 1;
                ms.obj = charSequence;
                mHandler.sendMessage(ms);
            }
        }).start();


//        Spanned spanned = Html.fromHtml(content, new Html.ImageGetter() {
//            @Override
//            public Drawable getDrawable(String source) {
//                Log.i(TAG, "getDrawable source: " + source);
//                Drawable drawable = null;
//                URL url;
//                try {
//                    url = new URL(source);
//                    drawable = Drawable.createFromStream(url.openStream(), "http://img3.imgtn.bdimg.com/it/u=1614455141,2952757874&fm=26&gp=0.jpg");  //获取网络图片
//                } catch (Exception e) {
//                    return null;
//                }
//                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
//                        .getIntrinsicHeight());
//                return drawable;
//            }
//        }, null);
//        tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
//        tv_content.setText(spanned);
//        tv_content.setText(Html.fromHtml(content));
        tv_time.setText(documentData.getAddTime().split("T")[0]);


        fileUpLoad = documentData.getUpload();
        photos.clear();
        records.clear();
        files.clear();
        if (!"".equals(fileUpLoad) && !"null".equals(fileUpLoad) && null != fileUpLoad) {
            String[] upload = fileUpLoad.split(",");
            for (int i = 0; i < upload.length; i++) {
                String s = upload[i];
                if (!"".equals(s)) {
                    if (s.endsWith(".jpg") || s.endsWith(".png") || s.endsWith(".jpeg")) {
                        photos.add(Globle.PHOTO_URL + s.trim());
                        photoAdapter = new EveGatherPhotoAdapter(photos);
                        rv_photo.setHasFixedSize(true);
                        rv_photo.setNestedScrollingEnabled(false);
                        rv_photo.setLayoutManager(new GridLayoutManager(this, 4));
                        rv_photo.setAdapter(photoAdapter);
                        photoAdapter.setOnGatherClick(new EveGatherPhotoAdapter.OnGatherClick() {
                            @Override
                            public void toGatherPhoto() {


                            }

                            @Override
                            public void toShowPhoto(int position) {
                                Intent intent = new Intent(mContext, ImageShowActivity.class);
                                intent.putExtra("position", position);
                                intent.putExtra("flag", "string");
                                List<String> picList = new ArrayList<>();
                                for (int i = 0; i < photos.size(); i++) {
                                    picList.add(photos.get(i));
                                }
                                intent.putStringArrayListExtra("data", (ArrayList<String>) picList);
                                startActivity(intent);
                            }

                            @Override
                            public void deletePhoto(int position) {

                            }
                        });

                    } else if (s.endsWith(".amr") || s.endsWith(".m4a") || s.endsWith(".wav")) {
                        RecordBean recordBean = new RecordBean();
                        recordBean.setPath(Globle.PHOTO_URL + s.trim());
                        recordBean.setPlayed(false);

                        records.add(recordBean);
                        recordAdapter = new EventRecordAdapter(mContext, records);
                        lv_record.setAdapter(recordAdapter);
                        recordAdapter.setOnRecordItemLongClick(new EventRecordAdapter.OnRecordItemLongClick() {
                            @Override
                            public void deleteRecord(int position) {

                            }
                        });
                    } else {
                        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mContext);
                        rv_file.setLayoutManager(linearLayoutManager1);
                        FileBean fileBean = new FileBean();
                        fileBean.setFilePath(Globle.PHOTO_URL + s.trim());
                        fileBean.setFileName(s.substring(s.lastIndexOf("/") + 1, s.length()));
                        files.add(fileBean);
                        fileAdapter = new EveGatherFileAdapter(mContext, files);
                        rv_file.setAdapter(fileAdapter);
                        fileAdapter.setOnFileItemLongClick(new EveGatherFileAdapter.OnFileItemLongClick() {
                            @Override
                            public void deleteFile(int position) {

                            }
                        });
                    }
                }
            }
        }

        receiveBeanList = send_receive;

    }


    @OnClick({R.id.ib_document_detail_back, R.id.ib_document_detail_transmit,
            R.id.fab_document_detail_report, R.id.ib_document_detail_receiver,
            R.id.fab_document_detail_save,
            R.id.ib_document_detail_edit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_document_detail_back:
                finish();
                break;
            case R.id.ib_document_detail_edit:
                Intent intent = new Intent(this, CreateDocumentActivity.class);
                intent.putExtra("document", documentData);
                intent.putExtra("flag", 2);
                startActivityForResult(intent, Globle.DOCUMENT_EDIT);
                break;
            case R.id.ib_document_detail_receiver:

                receiverList.clear();
                if (receiveBeanList != null && receiveBeanList.size() > 0) {
                    for (int i = 0; i < receiveBeanList.size(); i++) {
                        DocumentData.DataBean.SendReceiveBean receiveData = receiveBeanList.get(i);
                        String image = receiveData.getImage();
                        String realName = receiveData.getRealName();
                        receiverList.add(image + "," + realName);
                    }
                    new MaterialDialog.Builder(DocumentDetailActivity.this)
                            .items(receiverList)
                            .adapter(new EventAssignAdapter(DocumentDetailActivity.this, receiverList),
                                    new LinearLayoutManager(DocumentDetailActivity.this))
                            .show();
                } else {
                    ToastUtils.showToast(DocumentDetailActivity.this, "暂无公文接收者~~");
                }

                break;
            case R.id.ib_document_detail_transmit:   //转发 可多人0
                Intent memberIntent = new Intent(this, ChooseActivity.class);
                memberIntent.putExtra("flag", "member");
                memberIntent.putStringArrayListExtra("indexList", (ArrayList<String>) indexList);
                startActivityForResult(memberIntent, CommonUtils.PROJECT_CHOOSE_PERSON_MEMBER);
                break;
            case R.id.fab_document_detail_save:
                saveOrReport("保存公文！", "确定保存当前公文！", false);
                break;
            case R.id.fab_document_detail_report:

                Log.i(TAG, "onClick title: " + title);
                Log.i(TAG, "onClick content: " + content);
                Log.i(TAG, "onClick reportPerson: " + reportPerson);
                Log.i(TAG, "onClick fileUpLoad: " + fileUpLoad);
                Log.i(TAG, "onClick documentData.getId(): " + documentData.getId());
                Log.i(TAG, "onClick AdminDao.getUserID(): " + AdminDao.getUserID());


                saveOrReport("转发公文！", "确定转发当前公文！", true);

                break;

        }
    }

    private void saveOrReport(String dialogTitle, String dialogContent, boolean isSend) {
        new MaterialDialog.Builder(DocumentDetailActivity.this)
                .title(dialogTitle)
                .content(dialogContent)
                .positiveText("确定")
                .negativeText("取消")
                .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {
                            documentPresenter.createDocument(title,
                                    content,
                                    reportPerson,
                                    fileUpLoad,
                                    documentData.getId(),
                                    AdminDao.getUserID(),
                                    isSend);
                        } else if (which == DialogAction.NEGATIVE) {
                            dialog.dismiss();
                        }
                    }
                }).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CommonUtils.PROJECT_CHOOSE_PERSON_MEMBER) {    //事件执行者 多选
                assignPersonList.clear();
                pl_report.removeAllViews();
                List<String> list = new ArrayList<>();
                memberList = data.getParcelableArrayListExtra("choose");
                indexList = data.getStringArrayListExtra("indexList");
                Log.i(TAG, "onActivityResult: " + memberList.size() + memberList);

                for (int i = 0; i < memberList.size(); i++) {

                    imageView = (CircleImageView) inflater.inflate(R.layout.fuzeren_head_praise, pl_report, false);
                    if (i < 6) {
                        GlideHelper.loadNet(imageView, Globle.PHOTO_URL + memberList.get(i).getFrame().getImage(), R.mipmap.image_weibo_home_2);
                        pl_report.addView(imageView);
                    } else if (i == 6) {
                        ib_more.setVisibility(View.VISIBLE);
                    }
                    String assignPerson = memberList.get(i).getFrame().getId();
                    assignPersonList.add(assignPerson);

                    String image = memberList.get(i).getFrame().getImage();
                    String name = memberList.get(i).getFrame().getRealName();
                    list.add(image + "," + name);

                    pl_report.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new MaterialDialog.Builder(DocumentDetailActivity.this)
                                    .items(list)
                                    .adapter(new EventAssignAdapter(DocumentDetailActivity.this, list),
                                            new LinearLayoutManager(DocumentDetailActivity.this))
                                    .show();
                        }
                    });
                }

                String s2 = assignPersonList.toString();
                reportPerson = s2.substring(1, s2.length() - 1).trim();

                if (assignPersonList.size() > 0) {
                    ll_report.setVisibility(View.VISIBLE);
                    fam_create.setVisibility(View.VISIBLE);
                } else {
                    ll_report.setVisibility(View.GONE);
                    fam_create.setVisibility(View.GONE);
                }

            } else if (requestCode == Globle.DOCUMENT_EDIT) {
                if (flag == 10) {
                    documentPresenter.getDocumentDetail(messageNotifyData.getId());
                } else {
                    documentPresenter.getDocumentDetail(documentData.getId());
                }

            }
        }
    }

    @Override
    public void createDocument(String s) {
        Log.i(TAG, "createDocument: " + s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            String result = jsonObject.getString("Result");
            String message = jsonObject.getString("Message");
            if ("发送失败！".equals(message)) {
                Toast.makeText(this, "公文发送失败~", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "公文发送成功~", Toast.LENGTH_SHORT).show();
                finish();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void editDocument(String string) {
        Log.i(TAG, "editDocument: " + string);
    }

    @Override
    public void getDocumentDetail(String string) {
        Log.i(TAG, "getDocumentDetail: " + string);

        DocumentData.DataBean dataBean = new Gson().fromJson(string, new TypeToken<DocumentData.DataBean>() {
        }.getType());

        documentData = dataBean;
        initDocDetail(DocumentDetailActivity.this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (flag == 10) {
            documentPresenter.getDocumentDetail(messageNotifyData.getId());
        } else {
            documentPresenter.getDocumentDetail(documentData.getId());
        }

    }


}