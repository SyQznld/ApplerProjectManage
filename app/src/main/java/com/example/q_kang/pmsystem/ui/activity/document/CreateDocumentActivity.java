package com.example.q_kang.pmsystem.ui.activity.document;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.example.q_kang.pmsystem.modules.bean.bean.EventBusMessage;
import com.example.q_kang.pmsystem.modules.bean.bean.FileBean;
import com.example.q_kang.pmsystem.modules.bean.bean.RecordBean;
import com.example.q_kang.pmsystem.modules.bean.bean.ReturnFilePath;
import com.example.q_kang.pmsystem.modules.bean.bean.photo.ImageItemBean;
import com.example.q_kang.pmsystem.present.im.DocumentPresenter;
import com.example.q_kang.pmsystem.ui.activity.ChooseActivity;
import com.example.q_kang.pmsystem.ui.activity.FileActivity;
import com.example.q_kang.pmsystem.ui.activity.GatherPhotoActivity;
import com.example.q_kang.pmsystem.ui.activity.photo.ImageShowActivity;
import com.example.q_kang.pmsystem.ui.adpter.EventRecordAdapter;
import com.example.q_kang.pmsystem.ui.adpter.event.EveGatherFileAdapter;
import com.example.q_kang.pmsystem.ui.adpter.event.EveGatherPhotoAdapter;
import com.example.q_kang.pmsystem.ui.adpter.event.EventAssignAdapter;
import com.example.q_kang.pmsystem.ui.view.Utils.CommonUtils;
import com.example.q_kang.pmsystem.ui.view.Utils.EscapeUtils;
import com.example.q_kang.pmsystem.ui.view.Utils.FileUploadHttps;
import com.example.q_kang.pmsystem.ui.view.Utils.PermissionHelper;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.GlideHelper;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.MainHandler;
import com.example.q_kang.pmsystem.ui.view.Utils.WeiboDialogUtils;
import com.example.q_kang.pmsystem.ui.view.Utils.custom_view.record.AudioRecordButton;
import com.example.q_kang.pmsystem.ui.view.Utils.custom_view.record.MediaManager;
import com.example.q_kang.pmsystem.ui.view.Utils.imageloader.ImageSelectUtils;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.MyListView;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.PileLayout;
import com.example.q_kang.pmsystem.view.DocumentView;
import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.jimmy.common.util.ToastUtils;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class CreateDocumentActivity extends BaseActivity implements DocumentView, FileUploadHttps.FileUploadCallBack {


    @BindView(R.id.ib_document_back)
    ImageButton ib_back;
    @BindView(R.id.tv_create_document_title)
    TextView tv_title;
    @BindView(R.id.et_document_name)
    EditText et_name;
    @BindView(R.id.et_document_content)
    EditText et_content;
    @BindView(R.id.pl_document_receiver)
    PileLayout pl_receiver;
    @BindView(R.id.ib_document_receiver_more)
    ImageButton ib_receiver_more;
    @BindView(R.id.ib_document_receiver_add)
    ImageButton ib_receiver_add;
    @BindView(R.id.recy_event_gather_photo)
    RecyclerView rv_photo;
    @BindView(R.id.iv_add_record)
    ImageView iv_record;
    @BindView(R.id.lv_event_record)
    MyListView lv_record;
    @BindView(R.id.iv_add_file)
    ImageView iv_file;
    @BindView(R.id.rv_event_file)
    RecyclerView rv_file;
    @BindView(R.id.nsv_content)
    NestedScrollView nsv_content;
    @BindView(R.id.arb_document_btn)
    AudioRecordButton arbDocumentBtn;
    @BindView(R.id.rl_creat_document_record)
    RelativeLayout rl_record;
    @BindView(R.id.fab_document_save)
    FloatingActionButton fab_save;
    @BindView(R.id.fab_document_submit)
    FloatingActionButton fab_submit;


    private LayoutInflater inflater;
    private CircleImageView imageView;

    private ArrayList<ChooseData> memberList = new ArrayList<>();
    private ArrayList<FileBean> fileList = new ArrayList<>();
    private EveGatherFileAdapter fileAdapter;


    //录音
    private List<RecordBean> mRecords = new ArrayList<>();
    private EventRecordAdapter eventRecordAdapter;
    private PermissionHelper permissionHelper;

    private List<String> gatherPhotos = new ArrayList<>();
    private List<String> photos = new ArrayList<>();
    private EveGatherPhotoAdapter photoAdapter;
    private static final int REQUEST_CODE = 0x00000011;

    private String assignPerson, filePath;
    private List<String> assignPersonList = new ArrayList<>();

    private List<String> urlList = new ArrayList<>();


    private DocumentPresenter documentPresenter;
    private String userId;
    private boolean isSend;

    private int flag;
    private DocumentData.DataBean documentData;
    private String pid = "";

    //    private List<Integer> indexList = new ArrayList<>();
    private List<String> indexList = new ArrayList<>();

    private Dialog uploadDialog;

    private String detailContent;



    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    CharSequence charSequence = (CharSequence) msg.obj;
                    if (charSequence != null) {
                        et_content.setText(charSequence);
                        et_content.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public int bindLayout() {
        return R.layout.activity_create_document;
    }

    @Override
    public void doBusiness(Context mContext) {
        EventBus.getDefault().register(this);
        inflater = LayoutInflater.from(mContext);

        documentPresenter = new DocumentPresenter(this);
        userId = AdminDao.getUserID();

        //照片
        photos.add("");
        photoAdapter = new EveGatherPhotoAdapter(photos);
        rv_photo.setHasFixedSize(true);
        rv_photo.setNestedScrollingEnabled(false);
        rv_photo.setLayoutManager(new GridLayoutManager(this, 4));
        rv_photo.setAdapter(photoAdapter);
        photoAdapter.setOnGatherClick(new EveGatherPhotoAdapter.OnGatherClick() {
            @Override
            public void toGatherPhoto() {
                new MaterialDialog.Builder(CreateDocumentActivity.this)
                        .items(R.array.addPhotos)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                switch (position) {
                                    case 0://我的相册   从文件模块相册页面 选择
                                        Intent fileIntent = new Intent(CreateDocumentActivity.this, FileActivity.class);
                                        fileIntent.putExtra("choose", true);//是否选中
                                        startActivityForResult(fileIntent, CommonUtils.EVENT_CHOOSE_FILE);
                                        break;
                                    case 1: //系统相册
                                        // startActivityForResult(GatherPhotoActivity.class, null, 1);
                                        ImageSelectUtils.openPhoto(CreateDocumentActivity.this, REQUEST_CODE, false, 9);
                                        break;
                                    case 2://拍照
                                        startActivityForResult(GatherPhotoActivity.class, null, 1);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        })
                        .show();
            }

            @Override
            public void toShowPhoto(int position) {
                Intent intent = new Intent(mContext, ImageShowActivity.class);
                intent.putExtra("position", position - 1);
                intent.putExtra("flag", "string");
                List<String> picList = new ArrayList<>();
                for (int i = 1; i < photos.size(); i++) {
                    picList.add(photos.get(i));
                }
                intent.putStringArrayListExtra("data", (ArrayList<String>) picList);
                startActivity(intent);

            }

            @Override
            public void deletePhoto(int position) {

                new MaterialDialog.Builder(mContext)
                        .title("删除照片！")
                        .content("确定删除当前图片！")
                        .positiveText("确定")
                        .negativeText("取消")
                        .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which == DialogAction.POSITIVE) {

                                    String s = photos.get(position);
                                    photos.remove(position);
                                    String substring = s.substring(s.lastIndexOf("/"), s.length());
                                    for (int i = 0; i < urlList.size(); i++) {
                                        String s1 = urlList.get(i);
                                        Log.i("删除照片", "onClick: " + s1.contains(substring));
                                        if (s1.contains(substring)) {
                                            urlList.remove(i);
                                        }

                                    }
                                    photoAdapter.notifyDataSetChanged();

                                    dialog.dismiss();

                                } else if (which == DialogAction.NEGATIVE) {
                                    dialog.dismiss();
                                }
                            }
                        }).show();

            }
        });

        //录音
        eventRecordAdapter = new EventRecordAdapter(this, mRecords);
        lv_record.setAdapter(eventRecordAdapter);
        // Log.i(TAG, "doBusiness  mRecords : " + mRecords);
        eventRecordAdapter.setOnRecordItemLongClick(new EventRecordAdapter.OnRecordItemLongClick() {
            @Override
            public void deleteRecord(int position) {

                new MaterialDialog.Builder(CreateDocumentActivity.this)
                        .title("删除录音！")
                        .content("确定删除当前录音！")
                        .positiveText("确定")
                        .negativeText("取消")
                        .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which == DialogAction.POSITIVE) {

                                    String s = mRecords.get(position).getPath();
                                    mRecords.remove(position);
                                    String substring = s.substring(s.lastIndexOf("/"), s.length());
                                    for (int i = 0; i < urlList.size(); i++) {
                                        String s1 = urlList.get(i);
                                        if (s1.contains(substring)) {
                                            urlList.remove(i);
                                        }
                                    }
                                    eventRecordAdapter.notifyDataSetChanged();

                                    dialog.dismiss();

                                } else if (which == DialogAction.NEGATIVE) {
                                    dialog.dismiss();
                                }
                            }
                        }).show();
            }
        });


        //文件
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        rv_file.setLayoutManager(linearLayoutManager);
        fileAdapter = new EveGatherFileAdapter(mContext, fileList);
        rv_file.setAdapter(fileAdapter);
        fileAdapter.setOnFileItemLongClick(new EveGatherFileAdapter.OnFileItemLongClick() {
            @Override
            public void deleteFile(int position) {
                new MaterialDialog.Builder(CreateDocumentActivity.this)
                        .title("删除文件！")
                        .content("确定删除当前文件！")
                        .positiveText("确定")
                        .negativeText("取消")
                        .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which == DialogAction.POSITIVE) {

                                    String s = fileList.get(position).getFilePath();
                                    fileList.remove(position);
                                    String substring = s.substring(s.lastIndexOf("/"), s.length());
                                    for (int i = 0; i < urlList.size(); i++) {
                                        String s1 = urlList.get(i);
                                        if (s1.contains(substring)) {
                                            urlList.remove(i);
                                        }

                                    }
                                    fileAdapter.notifyDataSetChanged();

                                    dialog.dismiss();

                                } else if (which == DialogAction.NEGATIVE) {
                                    dialog.dismiss();
                                }
                            }
                        }).show();
            }
        });

        documentData = getIntent().getParcelableExtra("document");
        flag = getIntent().getIntExtra("flag", 0);
        if (flag == 2) {   //编辑
            Log.i(TAG, "doBusiness: " + documentData);
            tv_title.setText("编辑邮件");
            pid = documentData.getId();
            et_name.setText(documentData.getName());
            detailContent = documentData.getContent();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Html.ImageGetter imageGetter = new Html.ImageGetter() {

                        @Override
                        public Drawable getDrawable(String source) {
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
                    detailContent = detailContent.replace("\n", "<br />");
                    CharSequence charSequence = Html.fromHtml(detailContent, imageGetter, null);
                    Message ms = Message.obtain();
                    ms.what = 1;
                    ms.obj = charSequence;
                    mHandler.sendMessage(ms);
                }
            }).start();

            pl_receiver.removeAllViews();
            List<String> list = new ArrayList<>();
            List<DocumentData.DataBean.SendReceiveBean> send_receive = documentData.getSend_receive();

            if (send_receive != null && send_receive.size() > 0) {
                for (int i = 0; i < send_receive.size(); i++) {
                    DocumentData.DataBean.SendReceiveBean sendReceiveBean = send_receive.get(i);
                    String image = sendReceiveBean.getImage();
                    String realName = sendReceiveBean.getRealName();
                    if ("null".equals(image) || "null".equals(realName) || image == null || realName == null) {
                        list = new ArrayList<>();
                    } else {
                        list.add(image + "," + realName);

                        String receivePerson = sendReceiveBean.getReceivePerson();
                        assignPersonList.add(receivePerson);


                        imageView = (CircleImageView) inflater.inflate(R.layout.fuzeren_head_praise, pl_receiver, false);
                        if (i < 6) {
                            GlideHelper.loadNet(imageView, Globle.PHOTO_URL + image, R.mipmap.image_weibo_home_2);
                            pl_receiver.addView(imageView);
                        } else if (i == 6) {
                            ib_receiver_more.setVisibility(View.VISIBLE);
                        }

                        List<String> finalList = list;
                        pl_receiver.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new MaterialDialog.Builder(CreateDocumentActivity.this)
                                        .items(finalList)
                                        .adapter(new EventAssignAdapter(CreateDocumentActivity.this, finalList),
                                                new LinearLayoutManager(CreateDocumentActivity.this))
                                        .show();
                            }
                        });
                    }


                }

            } else {
                assignPersonList = new ArrayList<>();
            }

            indexList.addAll(assignPersonList);


            String upload1 = documentData.getUpload();
            if (!"".equals(upload1) && !"null".equals(upload1) && null != upload1) {
                String[] upload = upload1.split(",");
                for (int i = 0; i < upload.length; i++) {
                    String s = upload[i];
                    if (!"".endsWith(s)) {
                        if (s.endsWith(".jpg") || s.endsWith(".png") || s.endsWith(".jpeg")) {
                            photos.add(Globle.PHOTO_URL + s.trim());
                            urlList.add(s);
                        } else if (s.endsWith(".amr") || s.endsWith(".m4a") || s.endsWith(".wav")) {
                            RecordBean recordBean = new RecordBean();
                            recordBean.setPath(Globle.PHOTO_URL + s.trim());
                            recordBean.setPlayed(false);

                            mRecords.add(recordBean);
                            urlList.add(s);
                        } else {
                            FileBean fileBean = new FileBean();
                            fileBean.setFilePath(Globle.PHOTO_URL + s.trim());
                            fileBean.setFileName(s.substring(s.lastIndexOf("/") + 1, s.length()));
                            fileList.add(fileBean);
                            urlList.add(s);
                        }
                    }

                }
            }


        }


    }


    @OnClick({R.id.ib_document_back, R.id.ib_document_receiver_add,
            R.id.fab_document_save, R.id.fab_document_submit,
            R.id.iv_add_record, R.id.iv_add_file})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.ib_document_back:
                finish();
                break;
            case R.id.rl_creat_event_record:
                rl_record.setVisibility(View.GONE);
                break;
            case R.id.iv_add_record:

                new MaterialDialog.Builder(CreateDocumentActivity.this)
                        .items(R.array.addRecord)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                switch (position) {
                                    case 0:
                                        rl_record.setVisibility(View.VISIBLE);
                                        arbDocumentBtn.setHasRecordPromission(false);
                                        // 授权处理
                                        permissionHelper = new PermissionHelper(CreateDocumentActivity.this);
                                        permissionHelper.requestPermissions("请授予[录音]，[读写]权限，否则无法录音",
                                                new PermissionHelper.PermissionListener() {
                                                    @Override
                                                    public void doAfterGrand(String... permission) {
                                                        arbDocumentBtn.setHasRecordPromission(true);
                                                    }

                                                    @Override
                                                    public void doAfterDenied(String... permission) {
                                                        arbDocumentBtn.setHasRecordPromission(false);
                                                        Toast.makeText(CreateDocumentActivity.this, "请授权,否则无法录音", Toast.LENGTH_SHORT).show();
                                                    }
                                                }, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                        break;
                                    case 1:
                                        new LFilePicker()
                                                .withActivity(CreateDocumentActivity.this)
                                                .withRequestCode(CommonUtils.RECORD_CHOOSE_REQUESTCODE)
                                                .withIconStyle(Constant.ICON_STYLE_BLUE)
                                                .withFileFilter(new String[]{".amr", ".m4a", ".wav"})
                                                .withTitle("录音选择")
                                                .withBackgroundColor("#3395fa")
                                                .start();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        })
                        .show();

                break;
            case R.id.iv_add_file:
                new MaterialDialog.Builder(CreateDocumentActivity.this)
                        .items(R.array.addFiles)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                switch (position) {
                                    case 0:
                                        Intent fileIntent = new Intent(CreateDocumentActivity.this, FileActivity.class);
                                        fileIntent.putExtra("choose", true);//是否选中
                                        startActivityForResult(fileIntent, CommonUtils.EVENT_CHOOSE_FILE);
                                        break;
                                    case 1:
                                        new LFilePicker()
                                                .withActivity(CreateDocumentActivity.this)
                                                .withRequestCode(CommonUtils.FILE_CHOOSE_REQUESTCODE)
                                                .withIconStyle(Constant.ICON_STYLE_BLUE)
                                                .withTitle("文件选择")
                                                .withBackgroundColor("#3395fa")
                                                .start();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        })
                        .show();
                break;


            case R.id.ib_document_receiver_add:
                Intent memberIntent = new Intent(this, ChooseActivity.class);
                memberIntent.putExtra("flag", "member");
                memberIntent.putStringArrayListExtra("indexList", (ArrayList<String>) indexList);
//                memberIntent.putIntegerArrayListExtra("indexList", (ArrayList<Integer>) indexList);
                startActivityForResult(memberIntent, CommonUtils.PROJECT_CHOOSE_PERSON_MEMBER);
                break;
            case R.id.fab_document_save:
                isSend = false;
                postDocument("保存公文！", "确定保存当前公文！");
                break;
            case R.id.fab_document_submit:
                isSend = true;
                postDocument("提交公文！", "确定提交当前公文！");
                break;

        }
    }

    private void postDocument(String dialogTitle, String dialogContent) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i < photos.size(); i++) {
            String photo = photos.get(i);
            list.add(photo);
        }
        for (int i = 0; i < mRecords.size(); i++) {
            String record = mRecords.get(i).getPath();
            list.add(record);
        }
        for (int i = 0; i < fileList.size(); i++) {
            String file = fileList.get(i).getFilePath();
            list.add(file);
        }


        List<String> uploadList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).contains(Globle.PHOTO_URL)) {
                uploadList.add(list.get(i));
            }
        }
        String s1 = assignPersonList.toString();
        assignPerson = s1.substring(1, s1.length() - 1).trim();
        Log.i(TAG, "viewOnClick: " + assignPerson);
        new MaterialDialog.Builder(CreateDocumentActivity.this)
                .title(dialogTitle)
                .content(dialogContent)
                .positiveText("确定")
                .negativeText("取消")
                .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {
                            uploadDialog = WeiboDialogUtils.createLoadingDialog(CreateDocumentActivity.this, "正在提交");

                            if (uploadList.size() > 0) {
                                filePath = new Gson().toJson(uploadList);
                                FileUploadHttps.getInstance().requestFileUpload(filePath, CreateDocumentActivity.this);
                            } else {

                                String s1 = assignPersonList.toString();
                                assignPerson = s1.substring(1, s1.length() - 1).trim();
                                String substring = urlList.toString().substring(1, urlList.toString().length() - 1);

                                String title = et_name.getText().toString();
                                if (title.length() > 50) {
                                    ToastUtils.showToast(CreateDocumentActivity.this, "标题最大长度不能超过50字~");
                                }

                                String content = et_content.getText().toString();
                                if (content.contains("￼")) {      //编辑后内容字段有图片
//<p>邮件邮件邮件<img src="/Scripts/ueditor/net/upload/image/20181121/6367840894837395569033739.png" title="3c32c8c35910f7d1b378c79e5fc7011.png" alt="3c32c8c35910f7d1b378c79e5fc7011.png"/></p>
                                    String[] split = detailContent.split("<img");
                                    String s = split[0];
                                    String substring1 = s.substring(0, 3);
                                    String s2 = substring1 + content.substring(0, content.length() - 3) + "<img";
                                    content = s2 + split[1];
                                }
                                content = EscapeUtils.escape(content);
                                content = content.replace("+","%20");

                                if (!"".equals(title) && !"".equals(content) && !"".equals(assignPerson)) {
                                    Log.i(TAG, "onClick  flag: " + flag);
                                    Log.i(TAG, "onClick  assignPerson: " + assignPerson);
                                    Log.i(TAG, "onClick  content 加密后: " + content);
                                    if (flag == 2) {
                                        documentPresenter.editDocument(title, content, assignPerson, substring, pid, userId, isSend);
                                    } else {
                                        documentPresenter.createDocument(title, content, assignPerson, substring, pid, userId, isSend);
                                    }


                                } else {
                                    if (isSend) {
                                        if (title == null || "".equals(title)) {
                                            ToastUtils.showToast(CreateDocumentActivity.this, "公文名称不能为空~");
                                        } else if (content == null || "".equals(content)) {
                                            ToastUtils.showToast(CreateDocumentActivity.this, "公文内容不能为空~");
                                        } else if (assignPerson == null || "".equals(assignPerson)) {
                                            ToastUtils.showToast(CreateDocumentActivity.this, "公文收件人不能为空~");
                                        }
                                    } else {
                                        if (title == null || "".equals(title)) {
                                            ToastUtils.showToast(CreateDocumentActivity.this, "公文名称不能为空~");
                                        }
                                    }

//                                    Toast.makeText(CreateDocumentActivity.this, "请保持信息完整~", Toast.LENGTH_SHORT).show();
                                }

                            }
                        } else if (which == DialogAction.NEGATIVE) {
                            dialog.dismiss();
                        }
                    }
                }).show();
    }

    @Override
    public void createDocument(String s) {
        Log.i(TAG, "createDocument: " + s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            String code = jsonObject.getString("code");
            String message = jsonObject.getString("msg");
            String data = jsonObject.getString("data");


            if ("1".equals(code)) {
                Toast.makeText(this, "公文发送成功~", Toast.LENGTH_SHORT).show();
                finish();
            } else if ("2".equals(code)) {
                Toast.makeText(this, "公文保存成功~", Toast.LENGTH_SHORT).show();
                finish();
            } else if ("0".equals(message)) {
                Toast.makeText(this, "公文发送失败~", Toast.LENGTH_SHORT).show();
            }
            WeiboDialogUtils.closeDialog(uploadDialog);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void editDocument(String string) {
        Log.i(TAG, "editDocument: " + string);
        try {
            JSONObject jsonObject = new JSONObject(string);
            String code = jsonObject.getString("code");
            String message = jsonObject.getString("msg");
            String data = jsonObject.getString("data");

            if ("1".equals(code)) {
                Toast.makeText(this, "公文发送成功~", Toast.LENGTH_SHORT).show();
                finish();
            } else if ("2".equals(code)) {
                Toast.makeText(this, "公文保存成功~", Toast.LENGTH_SHORT).show();
                finish();
            } else if ("0".equals(message)) {
                Toast.makeText(this, "公文保存失败~", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getDocumentDetail(String string) {

    }

    @Override
    public void upFailure() {
        MainHandler.getInstance().post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CreateDocumentActivity.this, "附件上传失败~~~", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void upLoadSuccess(ReturnFilePath returnFilePath) {
        MainHandler.getInstance().post(new Runnable() {
            @Override
            public void run() {
                String path = returnFilePath.getSrc();
                urlList.add(path);
                String substring = urlList.toString().substring(1, urlList.toString().length() - 1);

                String msg = returnFilePath.getMsg();
                if ("上传成功".equals(msg)) {

                    String s1 = assignPersonList.toString();
                    assignPerson = s1.substring(1, s1.length() - 1).trim();

                    String title = et_name.getText().toString();
                    String content = et_content.getText().toString();

                    Log.i(TAG, "onClick title: " + title);
                    Log.i(TAG, "onClick content: " + content);
                    Log.i(TAG, "onClick assignPerson: " + assignPerson);

                    Log.i(TAG, "onClick substring: " + substring);
                    Log.i(TAG, "onClick pid: " + pid);
                    Log.i(TAG, "onClick isSend: " + isSend);

                    if (!"".equals(title) && !"".equals(content) && !"".equals(assignPerson)) {
                        Log.i(TAG, "onClick  flag: " + flag);
                        if (flag == 2) {
                            documentPresenter.editDocument(title, content, assignPerson, substring, pid, userId, isSend);
                        } else {
                            documentPresenter.createDocument(title, content, assignPerson, substring, pid, userId, isSend);
                        }

                    } else {
                        Toast.makeText(CreateDocumentActivity.this, "请保持信息完整~", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CreateDocumentActivity.this, "请保持信息完整~", Toast.LENGTH_SHORT).show();
                }

            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CommonUtils.PROJECT_CHOOSE_PERSON_MEMBER) {    //事件执行者 多选
                memberList.clear();
                pl_receiver.removeAllViews();
                assignPersonList.clear();
                List<String> list = new ArrayList<>();
                memberList = data.getParcelableArrayListExtra("choose");
//                indexList = data.getIntegerArrayListExtra("indexList");
                indexList = data.getStringArrayListExtra("indexList");
                Log.i(TAG, "onActivityResult: " + memberList.size() + memberList);
                for (int i = 0; i < memberList.size(); i++) {
                    imageView = (CircleImageView) inflater.inflate(R.layout.fuzeren_head_praise, pl_receiver, false);
                    if (i < 6) {
                        GlideHelper.loadNet(imageView, Globle.PHOTO_URL + memberList.get(i).getFrame().getImage(), R.mipmap.image_weibo_home_2);
                        pl_receiver.addView(imageView);
                    } else if (i == 6) {
                        ib_receiver_more.setVisibility(View.VISIBLE);
                    }
                    assignPerson = memberList.get(i).getFrame().getId();
                    assignPersonList.add(assignPerson);

                    String image = memberList.get(i).getFrame().getImage();
                    String name = memberList.get(i).getFrame().getRealName();
                    list.add(image + "," + name);

                    pl_receiver.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new MaterialDialog.Builder(CreateDocumentActivity.this)
                                    .items(list)
                                    .adapter(new EventAssignAdapter(CreateDocumentActivity.this, list),
                                            new LinearLayoutManager(CreateDocumentActivity.this))
                                    .show();
                        }
                    });
                }
            } else if (requestCode == CommonUtils.EVENT_CHOOSE_FILE) {//添加附件  从文件模块进行图片或文件选择
                ArrayList<FileBean> fileBeanArrayList = data.getParcelableArrayListExtra("fileChoose");
                ArrayList<ImageItemBean> imageList = data.getParcelableArrayListExtra("imageChoose");
                // Log.i(TAG, "onActivityResult: " + fileBeanArrayList + imageList);
                if (null != imageList) {
                    for (int i = 0; i < imageList.size(); i++) {
                        String imagePath = imageList.get(i).getImagePath();
                        if (imagePath.contains(".jpg") || imagePath.contains(".png") || imagePath.contains(".jpeg")) {
                            photos.add(imagePath);
                            photoAdapter.notifyDataSetChanged();
                        } else {
                            FileBean fileBean = new FileBean();
                            fileBean.setFilePath(imagePath);
                            fileBean.setFileName(imageList.get(i).getImageName());
                            fileList.add(fileBean);
                            fileAdapter.notifyDataSetChanged();
                        }

                    }
                }
                if (null != fileBeanArrayList) {
                    for (int i = 0; i < fileBeanArrayList.size(); i++) {
                        fileList.add(fileBeanArrayList.get(i));
                    }
                    fileAdapter.notifyDataSetChanged();
                }

            } else if (requestCode == 1) {//添加附件  拍照
                gatherPhotos = data.getStringArrayListExtra("gatherPhotos");
                photos.addAll(gatherPhotos);
                photoAdapter.notifyDataSetChanged();
            } else if (requestCode == REQUEST_CODE) {//添加附件  从系统图库选择图片
                ArrayList<String> images = data.getStringArrayListExtra(ImageSelectUtils.SELECT_RESULT);
                photos.addAll(images);
                photoAdapter.notifyDataSetChanged();
            } else if (requestCode == CommonUtils.FILE_CHOOSE_REQUESTCODE) {//添加附件  文件选择器从本地选择
                List<String> list = data.getStringArrayListExtra("paths");
                for (int i = 0; i < list.size(); i++) {
                    FileBean fileBean = new FileBean();
                    String s = list.get(i);
                    File file = new File(s);
                    fileBean.setFileName(file.getName());
                    fileBean.setFilePath(file.getAbsolutePath());
                    fileBean.setChooseTime(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
                    fileList.add(fileBean);
                }
                fileAdapter.notifyDataSetChanged();
            } else if (requestCode == CommonUtils.RECORD_CHOOSE_REQUESTCODE) {   //添加附件  选择录音
                List<String> list = data.getStringArrayListExtra("paths");

                rl_record.setVisibility(View.GONE);
                nsv_content.setVisibility(View.VISIBLE);

                for (int i = 0; i < list.size(); i++) {
                    String recordPath = new File(list.get(i)).getAbsolutePath();
                    float recordTime = 3;
                    RecordBean recordBean = new RecordBean();
                    recordBean.setPath(recordPath);
                    recordBean.setSecond((int) recordTime <= 0 ? 1 : (int) recordTime);
                    recordBean.setPlayed(false);
                    mRecords.add(recordBean);

                }
                eventRecordAdapter.notifyDataSetChanged();
            }
        }
    }


    /**
     * EventBus监听
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(EventBusMessage messageEvent) {
        switch (messageEvent.getMessage()) {
            case "record":
                String recordPath = messageEvent.getRecordPath();
                float recordTime = messageEvent.getRecordTime();

                rl_record.setVisibility(View.GONE);
                nsv_content.setVisibility(View.VISIBLE);

                RecordBean recordBean = new RecordBean();
                recordBean.setPath(recordPath);
                recordBean.setSecond((int) recordTime <= 0 ? 1 : (int) recordTime);
                recordBean.setPlayed(false);
                mRecords.add(recordBean);

                eventRecordAdapter.notifyDataSetChanged();
                break;

        }
    }


    //直接把参数交给mHelper就行了
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onPause() {
        MediaManager.release();//保证在退出该页面时，终止语音播放
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
