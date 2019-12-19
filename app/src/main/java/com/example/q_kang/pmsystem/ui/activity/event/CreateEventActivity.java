package com.example.q_kang.pmsystem.ui.activity.event;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.q_kang.pmsystem.modules.bean.bean.EventBusMessage;
import com.example.q_kang.pmsystem.modules.bean.bean.FileBean;
import com.example.q_kang.pmsystem.modules.bean.bean.RecordBean;
import com.example.q_kang.pmsystem.modules.bean.bean.ReturnFilePath;
import com.example.q_kang.pmsystem.modules.bean.bean.event.EventData;
import com.example.q_kang.pmsystem.modules.bean.bean.photo.ImageItemBean;
import com.example.q_kang.pmsystem.modules.bean.bean.work.WorkData;
import com.example.q_kang.pmsystem.present.im.EventPresenter;
import com.example.q_kang.pmsystem.ui.activity.ChooseActivity;
import com.example.q_kang.pmsystem.ui.activity.FileActivity;
import com.example.q_kang.pmsystem.ui.activity.GatherPhotoActivity;
import com.example.q_kang.pmsystem.ui.activity.map.BaseMapActivity;
import com.example.q_kang.pmsystem.ui.activity.photo.ImageShowActivity;
import com.example.q_kang.pmsystem.ui.adpter.EventRecordAdapter;
import com.example.q_kang.pmsystem.ui.adpter.event.EveGatherFileAdapter;
import com.example.q_kang.pmsystem.ui.adpter.event.EveGatherPhotoAdapter;
import com.example.q_kang.pmsystem.ui.adpter.event.EventAssignAdapter;
import com.example.q_kang.pmsystem.ui.view.Utils.CommonUtils;
import com.example.q_kang.pmsystem.ui.view.Utils.DataPick;
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
import com.example.q_kang.pmsystem.view.CreateEventView;
import com.example.q_kang.pmsystem.view.EventEditView;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class CreateEventActivity extends BaseActivity implements CreateEventView, FileUploadHttps.FileUploadCallBack {

    @BindView(R.id.ib_create_event_back)
    ImageButton ib_back;
    @BindView(R.id.tv_event_submit)
    TextView tv_submit;
    @BindView(R.id.tv_event_work)
    TextView tv_belong_work;
    @BindView(R.id.ll_event_project)
    LinearLayout ll_project;
    @BindView(R.id.tv_event_project)
    TextView tv_belong_project;
    @BindView(R.id.et_event_namebt)
    EditText et_bt;
    @BindView(R.id.et_event_name)
    EditText et_name;
    @BindView(R.id.et_event_type)
    EditText et_label;
    @BindView(R.id.civ_event_create_head)
    CircleImageView civ_create;
    @BindView(R.id.tv_event_create)
    TextView tv_create;
    @BindView(R.id.ib_event_create_add)
    ImageButton ib_create_add;
    @BindView(R.id.pl_event_member)
    PileLayout pile_member;
    @BindView(R.id.ib_event_member_more)
    ImageButton ib_member_more;
    @BindView(R.id.ib_event_member_add)
    ImageButton ib_member_add;
    @BindView(R.id.tv_event_time_start)
    EditText tv_start;
    @BindView(R.id.tv_event_time_end)
    EditText tv_end;
    @BindView(R.id.tv_event_location)
    TextView tv_location;
    @BindView(R.id.et_event_describe)
    EditText et_remark;
    @BindView(R.id.tv_event_choose)
    TextView tv_file_choose;
    @BindView(R.id.ll_event_add)
    LinearLayout ll_add;
    @BindView(R.id.iv_add_record)
    ImageView iv_add_record;
    @BindView(R.id.iv_add_file)
    ImageView iv_add_file;
    @BindView(R.id.rv_event_add)
    RecyclerView rv_add;

    @BindView(R.id.lv_event_record)
    MyListView lv_record;
    @BindView(R.id.rl_creat_event_show)
    RelativeLayout rl_show;
    @BindView(R.id.rl_creat_event_record)
    RelativeLayout rl_record;
    @BindView(R.id.ab_em_tv_btn)
    AudioRecordButton recordButton;
    @BindView(R.id.recy_event_gather_photo)
    RecyclerView recyEventGatherPhoto;
    @BindView(R.id.rv_event_file)
    RecyclerView rv_file;
    @BindView(R.id.tv_event_creat_title)
    TextView tv_title;
    @BindView(R.id.tv_event_canyu)
    TextView tv_canyu;


    private WorkData.DataBean workBean;

    private ArrayList<ChooseData> choosePerson = new ArrayList<>();
    private ArrayList<ChooseData> memberList = new ArrayList<>();
    private LayoutInflater inflater;
    private CircleImageView imageView;

    private int flag;

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

    private EventPresenter eventPresenter;

    private String pId = "";
    private String launchPerson = "";
    private String locationLatlng = "";
    private String address = "";
    private String nextId = "";
    private String assignPerson, filePath;
    private List<String> assignPersonList = new ArrayList<>();
    private List<String> nameList;

    private EventData.DataBean eventData;

    private List<String> urlList = new ArrayList<>();

    //    private List<Integer> indexList = new ArrayList<>();
    private List<String> indexList = new ArrayList<>();

    private Dialog uploadDialog;


    @Override
    public int bindLayout() {
        return R.layout.activity_creat_event;
    }

    @Override
    public void doBusiness(Context mContext) {
        EventBus.getDefault().register(this);

        tv_start.setFocusable(false);
        tv_end.setFocusable(false);

        inflater = LayoutInflater.from(mContext);
        workBean = getIntent().getParcelableExtra("work");//与工作有关，新建事件

        flag = getIntent().getIntExtra("flag", 0);
        eventData = getIntent().getParcelableExtra("event");
        nextId = getIntent().getStringExtra("nextId");
        Log.i(TAG, "doBusiness nextId: " + nextId);


        //照片
        // Log.i(TAG, "doBusiness  photos: " + photos);
        photos.add("");
        photoAdapter = new EveGatherPhotoAdapter(photos);
        recyEventGatherPhoto.setHasFixedSize(true);
        recyEventGatherPhoto.setNestedScrollingEnabled(false);
        recyEventGatherPhoto.setLayoutManager(new GridLayoutManager(this, 4));
        recyEventGatherPhoto.setAdapter(photoAdapter);
        photoAdapter.setOnGatherClick(new EveGatherPhotoAdapter.OnGatherClick() {
            @Override
            public void toGatherPhoto() {
                new MaterialDialog.Builder(CreateEventActivity.this)
                        .items(R.array.addPhotos)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                switch (position) {
                                    case 0://系统相册   从文件模块相册页面 选择
                                        Intent fileIntent = new Intent(CreateEventActivity.this, FileActivity.class);
                                        fileIntent.putExtra("choose", true);//是否选中
                                        startActivityForResult(fileIntent, CommonUtils.EVENT_CHOOSE_FILE);
                                        break;
                                    case 1: //本机相册
                                        // startActivityForResult(GatherPhotoActivity.class, null, 1);
                                        ImageSelectUtils.openPhoto(CreateEventActivity.this, REQUEST_CODE, false, 9);
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

                new MaterialDialog.Builder(CreateEventActivity.this)
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
                new MaterialDialog.Builder(CreateEventActivity.this)
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


        if (flag == 1) {            //事件列表直接新建 ，选择工作与项目
            tv_belong_work.setText("点击选择工作");
            tv_belong_work.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ll_project.setVisibility(View.GONE);
                    Intent intent = new Intent(mContext, ChooseBelongWorkActivity.class);
                    startActivityForResult(intent, CommonUtils.EVENT_CREATE_CHOOSE_WORK);
                }
            });
        } else if (flag == 2) {    //事件详情编辑
            tv_title.setText("事件编辑");
            pId = eventData.getpId();
            launchPerson = eventData.getLeaderId();
            assignPerson = eventData.getAssignPerson();
            if (!"".equals(assignPerson) && !"null".equals(assignPerson) && null != assignPerson) {
                String[] split = assignPerson.split(",");
                for (int i = 0; i < split.length; i++) {
                    indexList.add(split[i]);
                }
            }

            String[] split = assignPerson.split(",");
            for (int i = 0; i < split.length; i++) {
                assignPersonList.add(split[i]);
            }
            String workName = eventData.getWorkName();
            if ("null".equals(workName) || null == workName || "".equals(workName)) {
                workName = "暂无所属工作";
            }
            tv_belong_work.setText(workName + "    (点击选择工作)");
            tv_belong_work.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ll_project.setVisibility(View.GONE);
                    Intent intent = new Intent(mContext, ChooseBelongWorkActivity.class);
                    startActivityForResult(intent, CommonUtils.EVENT_CREATE_CHOOSE_WORK);
                }
            });
            et_bt.setText(eventData.getTitle());
            et_name.setText(eventData.getContent());
            et_label.setText(eventData.getLable());
            GlideHelper.loadNet(civ_create, Globle.PHOTO_URL + eventData.getImage(), R.mipmap.image_weibo_home_2);
            tv_create.setText(eventData.getLeaderPerson());
            //事件参与人员
            List<String> list = new ArrayList<>();
            String imagePerson = eventData.getImagePerson();
            Log.i(TAG, "eventData.getImagePerson() eventData.getImagePerson(): " + imagePerson);
            if (!"null".equals(imagePerson) && !"".equals(imagePerson) && imagePerson != null) {

                String[] split1 = imagePerson.split(",");
                int member = split1.length;
                Log.i(TAG, "doBusiness split.length: " + member);

                pile_member.removeAllViews();
                if (member == 0) {
                    for (int j = 0; j < eventData.getAssignPerson().split(",").length; j++) {
                        imageView = (CircleImageView) inflater.inflate(R.layout.fuzeren_head_praise, pile_member, false);
                        GlideHelper.loadNet(imageView, " ", R.mipmap.image_weibo_home_2);
                        pile_member.addView(imageView);
                    }
                } else {
                    for (int j = 0; j < member; j++) {
                        imageView = (CircleImageView) inflater.inflate(R.layout.fuzeren_head_praise, pile_member, false);
                        GlideHelper.loadNet(imageView, Globle.PHOTO_URL + split1[j], R.mipmap.image_weibo_home_2);
                        pile_member.addView(imageView);
                    }
                }


                String assignPersons = eventData.getAssignPersons();
                String imagePerson1 = eventData.getImagePerson();
                if ("".equals(assignPersons) || "null".equals(assignPersons) || null == assignPersons) {
                    assignPersons = "";
                }
                if ("".equals(imagePerson1) || "null".equals(imagePerson1) || null == imagePerson1) {
                    imagePerson1 = "";
                }
                String[] name = assignPersons.split(",");
                String[] image = imagePerson1.split(",");

                if (image.length == 0) {
                    for (int i = 0; i < name.length; i++) {
                        String s = " ";
                        String s1 = name[i];
                        list.add(s + "," + s1);
                    }
                } else {
                    for (int i = 0; i < name.length; i++) {
                        String s = image[i];
                        String s1 = name[i];
                        list.add(s + "," + s1);
                    }
                }


//
//                pile_member.removeAllViews();
//                for (int j = 0; j < split1.length; j++) {
//                    imageView = (CircleImageView) inflater.inflate(R.layout.fuzeren_head_praise, pile_member, false);
//                    GlideHelper.loadNet(imageView, Globle.PHOTO_URL + split1[j], R.mipmap.image_weibo_home_2);
//                    pile_member.addView(imageView);
//
//
//                    String image = split1[j];
//                    String name = eventData.getAssignPersons().split(",")[j];
//                    list.add(image + "," + name);
//
//                }
                pile_member.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new MaterialDialog.Builder(CreateEventActivity.this)
                                .items(list)
                                .adapter(new EventAssignAdapter(CreateEventActivity.this, list),
                                        new LinearLayoutManager(CreateEventActivity.this))
                                .show();
                    }
                });
            }

            tv_canyu.setText(eventData.getAssignPersons());
            tv_start.setText(eventData.getStartTime().split("T")[0]);
            tv_end.setText(eventData.getEndTime().split("T")[0]);

            String upload1 = eventData.getUpload();
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


            locationLatlng = eventData.getLocation();
            address = eventData.getAddress();
            if ("".equals(address) || "null".equals(address) || null == address) {
                address = "";
            } else {
                address = address.trim();
            }
            tv_location.setText(address + "(点击选择)");
//            if (locationLatlng != null && !"".equals(locationLatlng)) {
//                tv_location.setText("(点击选择事件位置)");
//            }


        } else {   //flag = 3     工作下添加事件

            pId = workBean.getId();
            tv_belong_work.setText(workBean.getName());
            String selectProject = workBean.getProjectName();
            if (!"".equals(selectProject) && selectProject != null) {
                ll_project.setVisibility(View.VISIBLE);
                tv_belong_project.setText(selectProject);
            }
        }


    }

    @OnClick({R.id.ib_create_event_back, R.id.tv_event_submit, R.id.tv_event_work
            , R.id.ib_event_create_add, R.id.ib_event_member_add, R.id.tv_event_time_start
            , R.id.tv_event_time_end, R.id.tv_event_location, R.id.iv_add_record
            , R.id.rl_creat_event_record, R.id.iv_add_file})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.ib_create_event_back:
                finish();
                break;
            case R.id.tv_event_submit:

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
                new MaterialDialog.Builder(CreateEventActivity.this)
                        .title("提交事件！")
                        .content("确定提交当前事件！")
                        .positiveText("确定")
                        .negativeText("取消")
                        .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which == DialogAction.POSITIVE) {
                                   uploadDialog =  WeiboDialogUtils.createLoadingDialog(CreateEventActivity.this,"正在提交");
                                    if (uploadList.size() > 0) {
                                        filePath = new Gson().toJson(uploadList);
                                        FileUploadHttps.getInstance().requestFileUpload(filePath, CreateEventActivity.this);
                                    } else {

                                        String s1 = assignPersonList.toString();
                                        assignPerson = s1.substring(1, s1.length() - 1).trim();
                                        String substring = urlList.toString().substring(1, urlList.toString().length() - 1);

                                        String title = et_bt.getText().toString();
                                        String content = et_name.getText().toString();
                                        String start = tv_start.getText().toString();
                                        String end = tv_end.getText().toString();
                                        String label = et_label.getText().toString();
                                        Log.i(TAG, "onClick pId: " + pId);
                                        Log.i(TAG, "onClick title: " + title);
                                        Log.i(TAG, "onClick content: " + content);
                                        Log.i(TAG, "onClick start: " + start);
                                        Log.i(TAG, "onClick end: " + end);
                                        Log.i(TAG, "onClick launchPerson: " + launchPerson);
                                        Log.i(TAG, "onClick assignPerson: " + assignPerson);
                                        Log.i(TAG, "onClick locationLatlng: " + locationLatlng);
                                        Log.i(TAG, "onClick substring: " + substring);

                                        try {
                                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                            Date dt1 = df.parse(start);
                                            Date dt2 = df.parse(end);
                                            long time = dt1.getTime() - dt2.getTime();
                                            Log.i(TAG, "onClick: " + time);
                                            if (time > 0) {
                                                ToastUtils.showToast(CreateEventActivity.this, "请保证结束时间在开始时间之后~");
                                            } else {


                                                if (!"".equals(title) && !"".equals(content) && !"".equals(start) && !"".equals(end)
                                                        && !"".equals(launchPerson) && !"".equals(assignPerson) && !"".equals(locationLatlng)) {
                                                    if (flag == 2) {   //事件编辑
                                                        eventPresenter = new EventPresenter(new EventEditView() {
                                                            @Override
                                                            public void editEvent(String string) {
                                                                try {
                                                                    JSONObject jsonObject = new JSONObject(string);
                                                                    String result = jsonObject.getString("Result");
                                                                    String message = jsonObject.getString("Message");
                                                                    if ("保存成功！".equals(message)) {
                                                                        Toast.makeText(CreateEventActivity.this, "事件编辑成功", Toast.LENGTH_SHORT).show();
                                                                        Intent intent = new Intent();
                                                                        setResult(RESULT_OK, intent);
                                                                        finish();
                                                                    } else {
                                                                        Toast.makeText(CreateEventActivity.this, "事件编辑失败", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }

                                                            @Override
                                                            public void setState(int state) {
                                                            }
                                                        });
                                                        Log.i(TAG, "onClick pId: " + pId);
                                                        eventPresenter.editEvent(pId, eventData.getId(), title, content, start, end, AdminDao.getUserID(),
                                                                launchPerson, assignPerson, locationLatlng, address, substring,
                                                                "0", label, nextId);
                                                    } else {           //事件新建
                                                        Log.i(TAG, "onClick 新建pId: " + pId);

                                                        eventPresenter = new EventPresenter(CreateEventActivity.this);
                                                        eventPresenter.createEvent(pId, title, content, start, end, AdminDao.getUserID(),
                                                                launchPerson, assignPerson, locationLatlng, address, substring,
                                                                "0", label, nextId);

                                                    }
                                                } else {

                                                    if (title == null || "".equals(title)) {
                                                        ToastUtils.showToast(CreateEventActivity.this, "事件标题不能为空~");
                                                    } else if (content == null || "".equals(content)) {
                                                        ToastUtils.showToast(CreateEventActivity.this, "事件内容不能为空~");
                                                    } else if (launchPerson == null || "".equals(launchPerson)) {
                                                        ToastUtils.showToast(CreateEventActivity.this, "事件负责人不能为空~");
                                                    } else if (assignPerson == null || "".equals(assignPerson)) {
                                                        ToastUtils.showToast(CreateEventActivity.this, "事件参与人员不能为空~");
                                                    } else if (start == null || "".equals(start)) {
                                                        ToastUtils.showToast(CreateEventActivity.this, "事件开始时间不能为空~");
                                                    } else if (end == null || "".equals(end)) {
                                                        ToastUtils.showToast(CreateEventActivity.this, "事件结束时间不能为空~");
                                                    } else if (locationLatlng == null || "".equals(locationLatlng)) {
                                                        ToastUtils.showToast(CreateEventActivity.this, "事件位置不能为空~");
                                                    }


//                                            Toast.makeText(CreateEventActivity.this, "请保持信息完整~", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } else if (which == DialogAction.NEGATIVE) {
                                    dialog.dismiss();
                                }
                            }
                        }).show();

                break;
            case R.id.ib_event_create_add:
                Intent fuzerenIntent = new Intent(this, ChooseActivity.class);
                fuzerenIntent.putExtra("flag", "fuzeren");
                startActivityForResult(fuzerenIntent, CommonUtils.PROJECT_CHOOSE_PERSON_FUZEREN);
                break;
            case R.id.ib_event_member_add:
                Intent memberIntent = new Intent(this, ChooseActivity.class);
                memberIntent.putExtra("flag", "member");
//                memberIntent.putIntegerArrayListExtra("indexList", (ArrayList<Integer>) indexList);
                memberIntent.putStringArrayListExtra("indexList", (ArrayList<String>) indexList);
                startActivityForResult(memberIntent, CommonUtils.PROJECT_CHOOSE_PERSON_MEMBER);
                break;
            case R.id.tv_event_time_start:
                new DataPick(this, "事件开始时间", tv_start);
                break;
            case R.id.tv_event_time_end:
                new DataPick(this, "事件结束时间", tv_end);
                break;
            case R.id.tv_event_location:
                Intent locationIntent = new Intent(this, BaseMapActivity.class);
                locationIntent.putExtra("map", "project");
                startActivityForResult(locationIntent, CommonUtils.PROJECT_MAP_LOCATION_CODE);
                break;
            case R.id.rl_creat_event_record:
                rl_record.setVisibility(View.GONE);
                break;
            case R.id.iv_add_record:
                new MaterialDialog.Builder(CreateEventActivity.this)
                        .items(R.array.addRecord)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                switch (position) {
                                    case 0:
                                        rl_record.setVisibility(View.VISIBLE);
                                        recordButton.setHasRecordPromission(false);
                                        // 授权处理
                                        permissionHelper = new PermissionHelper(CreateEventActivity.this);
                                        permissionHelper.requestPermissions("请授予[录音]，[读写]权限，否则无法录音",
                                                new PermissionHelper.PermissionListener() {
                                                    @Override
                                                    public void doAfterGrand(String... permission) {
                                                        recordButton.setHasRecordPromission(true);
                                                    }

                                                    @Override
                                                    public void doAfterDenied(String... permission) {
                                                        recordButton.setHasRecordPromission(false);
                                                        Toast.makeText(CreateEventActivity.this, "请授权,否则无法录音", Toast.LENGTH_SHORT).show();
                                                    }
                                                }, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                        break;
                                    case 1:
                                        new LFilePicker()
                                                .withActivity(CreateEventActivity.this)
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
                new MaterialDialog.Builder(CreateEventActivity.this)
                        .items(R.array.addFiles)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                switch (position) {
                                    case 0:
                                        Intent fileIntent = new Intent(CreateEventActivity.this, FileActivity.class);
                                        fileIntent.putExtra("choose", true);//是否选中
                                        startActivityForResult(fileIntent, CommonUtils.EVENT_CHOOSE_FILE);
                                        break;
                                    case 1:
                                        new LFilePicker()
                                                .withActivity(CreateEventActivity.this)
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
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == CommonUtils.PROJECT_MAP_LOCATION_CODE) {//返回新建事件 选取位置
                address = data.getStringExtra("address");


                double latitude = data.getDoubleExtra("latitude", 0);
                double longitude = data.getDoubleExtra("longitude", 0);
                if (!"".equals(address) && address != null) {
                    tv_location.setText(address);
                } else {
                    tv_location.setText("未获取到位置信息");
                }

                locationLatlng = "point(" + longitude + "," + latitude + ")";

            } else if (requestCode == CommonUtils.PROJECT_CHOOSE_PERSON_FUZEREN) {//事件创建者 单选
                choosePerson.clear();
                choosePerson = data.getParcelableArrayListExtra("choose");
                for (int i = 0; i < choosePerson.size(); i++) {
                    tv_create.setText(choosePerson.get(i).getFrame().getRealName());
                    GlideHelper.loadNet(civ_create, Globle.PHOTO_URL + choosePerson.get(i).getFrame().getImage(), R.mipmap.image_weibo_home_2);
                    launchPerson = choosePerson.get(i).getFrame().getId();
                }

            } else if (requestCode == CommonUtils.PROJECT_CHOOSE_PERSON_MEMBER) {    //事件执行者 多选
                assignPersonList.clear();
                memberList.clear();
                tv_canyu.setVisibility(View.GONE);
                List<String> list = new ArrayList<>();
                pile_member.removeAllViews();
                memberList = data.getParcelableArrayListExtra("choose");
                indexList = data.getStringArrayListExtra("indexList");
//                indexList = data.getIntegerArrayListExtra("indexList");
                nameList = new ArrayList<>();

                for (int i = 0; i < memberList.size(); i++) {
                    imageView = (CircleImageView) inflater.inflate(R.layout.fuzeren_head_praise, pile_member, false);
                    if (i < 6) {
                        GlideHelper.loadNet(imageView, Globle.PHOTO_URL + memberList.get(i).getFrame().getImage(), R.mipmap.image_weibo_home_2);
                        pile_member.addView(imageView);
                    } else if (i == 6) {
                        ib_member_more.setVisibility(View.VISIBLE);
                    }
                    assignPerson = memberList.get(i).getFrame().getId();
                    assignPersonList.add(assignPerson);


                    String image = memberList.get(i).getFrame().getImage();
                    String name = memberList.get(i).getFrame().getRealName();
                    list.add(image + "," + name);

                    pile_member.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new MaterialDialog.Builder(CreateEventActivity.this)
                                    .items(list)
                                    .adapter(new EventAssignAdapter(CreateEventActivity.this, list),
                                            new LinearLayoutManager(CreateEventActivity.this))
                                    .show();
                        }
                    });

                    String realName = memberList.get(i).getFrame().getRealName();
                    nameList.add(realName);
                }
                String s1 = nameList.toString();
                String name = s1.substring(1, s1.length() - 1).trim();
                tv_canyu.setText(name);
            } else if (requestCode == CommonUtils.EVENT_CREATE_CHOOSE_WORK) {    //事件 所属工作
                choosePerson = data.getParcelableArrayListExtra("choose");
                for (int i = 0; i < choosePerson.size(); i++) {
                    String belongWork = choosePerson.get(i).getBelongWork().getName();
                    if (!"".equals(belongWork) && null != belongWork && !"独立事件".equals(belongWork)) {
                        tv_belong_work.setText(belongWork);
                        pId = choosePerson.get(i).getBelongWork().getId();
                    } else {
                        tv_belong_work.setText("独立事件，暂无所属工作");
                        pId = "";
                    }
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

//                if (null == fileBeanArrayList && imageList != null) {
//                    Toast.makeText(this, "选择了" + imageList.size() + "张图片，" + "0个文件", Toast.LENGTH_SHORT).show();
//                } else if (null == imageList && fileBeanArrayList != null) {
//                    Toast.makeText(this, "选择了0张图片, " + fileBeanArrayList.size() + "个文件", Toast.LENGTH_SHORT).show();
//                } else if (null != imageList && null != fileBeanArrayList) {
//                    Toast.makeText(this, "选择了" + imageList.size() + "张图片, " + fileBeanArrayList.size() + "个文件", Toast.LENGTH_SHORT).show();
//                }
            } else if (requestCode == 1) {//添加附件  拍照
                gatherPhotos = data.getStringArrayListExtra("gatherPhotos");
                photos.addAll(gatherPhotos);
                photoAdapter.notifyDataSetChanged();
            } else if (requestCode == REQUEST_CODE) {//添加附件  从系统图库选择图片
                ArrayList<String> images = data.getStringArrayListExtra(ImageSelectUtils.SELECT_RESULT);
                Log.i(TAG, "onActivityResult: " + images.size() + images);
                photos.addAll(images);
                photoAdapter.notifyDataSetChanged();
            } else if (requestCode == CommonUtils.FILE_CHOOSE_REQUESTCODE) {//添加附件  文件选择器从本地选择
                List<String> list = data.getStringArrayListExtra("paths");
                for (int i = 0; i < list.size(); i++) {
                    String s = list.get(i);
                    FileBean fileBean = new FileBean();
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
                rl_show.setVisibility(View.VISIBLE);

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
                rl_show.setVisibility(View.VISIBLE);

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


    @Override
    public void createEvent(String string) {
        Log.i(TAG, "createEvent: " + string);
        try {
            JSONObject jsonObject = new JSONObject(string);
            String result = jsonObject.getString("Result");
            String message = jsonObject.getString("Message");
            if ("保存成功！".equals(message)) {
                Toast.makeText(this, "事件创建成功", Toast.LENGTH_SHORT).show();
                finish();
            } else if ("事件名已经存在！".equals(message)) {
                Toast.makeText(this, "事件名已经存在！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "事件创建失败", Toast.LENGTH_SHORT).show();
            }
            MainHandler.getInstance().post(new Runnable() {
                @Override
                public void run() {
                    WeiboDialogUtils.closeDialog(uploadDialog);
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getEventDetail(EventData.DataBean dataBean) {

    }

    @Override
    public void getEventComment(String string) {

    }

    @Override
    public void postComment(String string) {

    }


    @Override
    public void upFailure() {
        MainHandler.getInstance().post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CreateEventActivity.this, "附件上传失败~~~", Toast.LENGTH_SHORT).show();
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
                    Log.i(TAG, "run assignPerson: " + assignPerson);
                    String title = et_bt.getText().toString();
                    String content = et_name.getText().toString();
                    String start = tv_start.getText().toString();
                    String end = tv_end.getText().toString();
                    if (!"".equals(title) && !"".equals(content) && !"".equals(start) && !"".equals(end)
                            && !"".equals(launchPerson) && !"".equals(assignPerson) && !"".equals(locationLatlng)) {
                        if (flag == 2) {   //事件编辑
                            eventPresenter = new EventPresenter(new EventEditView() {
                                @Override
                                public void editEvent(String string) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(string);
                                        String result = jsonObject.getString("Result");
                                        String message = jsonObject.getString("Message");
                                        if ("保存成功！".equals(message)) {
                                            Toast.makeText(CreateEventActivity.this, "事件编辑成功", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent();
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        } else {
                                            Toast.makeText(CreateEventActivity.this, "事件编辑失败", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void setState(int state) {
                                }
                            });
                            // Log.i(TAG, "onClick pId: " + pId);
                            eventPresenter.editEvent(pId, eventData.getId(), title, content, start, end, AdminDao.getUserID(),
                                    launchPerson, assignPerson, locationLatlng, address, substring,
                                    "0", et_label.getText().toString(), nextId);
                        } else {           //事件新建

                            eventPresenter = new EventPresenter(CreateEventActivity.this);
                            eventPresenter.createEvent(pId, title, content, start, end, AdminDao.getUserID(),
                                    launchPerson, assignPerson, locationLatlng, address, substring,
                                    "0", et_label.getText().toString(), nextId);

                        }
                    } else {
                        Toast.makeText(CreateEventActivity.this, "请保持信息完整~", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

}