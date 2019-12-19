package com.example.q_kang.pmsystem.ui.activity.event;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
import com.example.q_kang.pmsystem.modules.bean.bean.AllShowData;
import com.example.q_kang.pmsystem.modules.bean.bean.FileBean;
import com.example.q_kang.pmsystem.modules.bean.bean.MessageNotifyData;
import com.example.q_kang.pmsystem.modules.bean.bean.RecordBean;
import com.example.q_kang.pmsystem.modules.bean.bean.event.EventComment;
import com.example.q_kang.pmsystem.modules.bean.bean.event.EventData;
import com.example.q_kang.pmsystem.present.im.EventDetailPresenter;
import com.example.q_kang.pmsystem.ui.activity.map.BaseMapActivity;
import com.example.q_kang.pmsystem.ui.activity.photo.ImageShowActivity;
import com.example.q_kang.pmsystem.ui.adpter.EventRecordAdapter;
import com.example.q_kang.pmsystem.ui.adpter.event.EveGatherFileAdapter;
import com.example.q_kang.pmsystem.ui.adpter.event.EveGatherPhotoAdapter;
import com.example.q_kang.pmsystem.ui.adpter.event.EventAssignAdapter;
import com.example.q_kang.pmsystem.ui.adpter.event.EventCommentAdapter;
import com.example.q_kang.pmsystem.ui.view.Utils.Utils.GlideHelper;
import com.example.q_kang.pmsystem.ui.view.Utils.custom_view.record.AudioRecordButton;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.MyListView;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.PileLayout;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.SearchLabelFlowLayout;
import com.example.q_kang.pmsystem.view.CreateEventView;
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

public class EventDetailActivity extends BaseActivity implements CreateEventView {
    @BindView(R.id.iv_event_detail_back)
    ImageView iv_back;
    @BindView(R.id.tv_event_detail_edit)
    TextView tv_edit;

    @BindView(R.id.tv_event_detail_bt)
    TextView tv_bt;
    @BindView(R.id.pl_event_detail_member)
    PileLayout pl_member;
    @BindView(R.id.iv_event_zhixingren_more_more)
    ImageView iv_more;
    @BindView(R.id.tv_event_detail_zhixingren)
    TextView tv_assign;
    @BindView(R.id.recy_event_detail_photo)
    RecyclerView rv_photo;
    @BindView(R.id.tv_event_detail_name)
    TextView tv_content;
    @BindView(R.id.tv_event_detail_belong)
    TextView tv_work;
    @BindView(R.id.iv_event_detail_complete)
    ImageView iv_complete;
    @BindView(R.id.tv_event_detail_start)
    TextView tv_start;
    @BindView(R.id.tv_event_detail_end)
    TextView tv_end;
    @BindView(R.id.civ_event_detail_launch)
    CircleImageView civ_fuzeren;
    @BindView(R.id.tv_event_detail_fuzeren)
    TextView tv_fuzeren;
    @BindView(R.id.lv_event_detail_record)
    MyListView lv_record;
    @BindView(R.id.rv_event_detail_file)
    RecyclerView rv_file;
    @BindView(R.id.iv_event_detail_location)
    ImageView iv_location;
    @BindView(R.id.tv_event_detail_label)
    TextView tv_label;
    @BindView(R.id.btn_edit)
    Button btn_submit;
    @BindView(R.id.ll_fuzeren)
    LinearLayout ll_fuzeren;
    @BindView(R.id.ll_zhixingren)
    LinearLayout ll_zhixingren;
    @BindView(R.id.rl_event)
    RelativeLayout rlEvent;
    @BindView(R.id.ab_em_tv_btn)
    AudioRecordButton abEmTvBtn;
    @BindView(R.id.rl_record)
    RelativeLayout rlRecord;
    @BindView(R.id.iv_record)
    ImageView ivRecord;
    @BindView(R.id.iv_file)
    ImageView ivFile;
    @BindView(R.id.fab_event_comment)
    FloatingActionButton fab_comment;
    @BindView(R.id.fab_event_edit_comment)
    FloatingActionButton fab_edit;
    @BindView(R.id.fab_event_complete)
    FloatingActionButton fab_complete;
    @BindView(R.id.tv_event_detail_canyu)
    TextView tv_canyu;

    @BindView(R.id.ll_label)
    SearchLabelFlowLayout ll_label;
    @BindView(R.id.tv_event_detail_comment)
    TextView tv_comment;
    @BindView(R.id.ll_comment)
    LinearLayout ll_comment;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.tv_comment_person)
    TextView tvCommentPerson;
    @BindView(R.id.fam_event_detail)
    FloatingActionMenu fam_munu;
    @BindView(R.id.rv_event_comment)
    RecyclerView rv_comment;

    private TextView[] textViews;//指示点集合
    private TextView textView;


    private CircleImageView imageView;
    private LayoutInflater inflater;

    private List<String> photos = new ArrayList<>();
    private EveGatherPhotoAdapter photoAdapter;

    private List<RecordBean> records = new ArrayList<>();
    private EventRecordAdapter recordAdapter;

    private List<FileBean> files = new ArrayList<>();
    private EveGatherFileAdapter fileAdapter;

    private EventData.DataBean event;

    private EventDetailPresenter eventPresenter;

    private int flag;
    private AllShowData alldata;
    private String marker_id;

    private List<EventComment.DataBean> commentList = new ArrayList<>();
    private EventCommentAdapter commentAdapter;

    private String commentId;
    private String addUserId;

    private MessageNotifyData.DataBean messageNotifyData;


    @Override
    public int bindLayout() {
        return R.layout.activity_event_detail;
    }

    @Override
    public void doBusiness(Context mContext) {
        inflater = LayoutInflater.from(this);


        eventPresenter = new EventDetailPresenter(EventDetailActivity.this);

        event = getIntent().getParcelableExtra("event");
        Log.i(TAG, "doBusiness: " + event);


        marker_id = getIntent().getStringExtra("marker_id");
        alldata = getIntent().getParcelableExtra("alldata");
        messageNotifyData = getIntent().getParcelableExtra("messagenotify");
        flag = getIntent().getIntExtra("flag", 0);
        Log.i(TAG, "doBusiness: " + flag);
//        if (flag == 4) {
//            eventPresenter.getEventDetail(alldata.getId());
//            eventPresenter.getEventComment(alldata.getId());
//        } else if (flag == 5) {
//            eventPresenter.getEventDetail(marker_id);
//            eventPresenter.getEventComment(marker_id);
//        } else if (flag == 10) {
//            Log.i(TAG, "doBusiness: " + messageNotifyData.getId());
//            eventPresenter.getEventDetail(messageNotifyData.getId());
//            eventPresenter.getEventComment(messageNotifyData.getId());
//        } else {
//            eventPresenter.getEventComment(event.getId());
//        }

        if (event != null) {
            initEventDetail(mContext);
        }


    }

    private void initEventDetail(Context mContext) {


        ll_fuzeren.setEnabled(false);
        ivRecord.setEnabled(false);
        ivFile.setEnabled(false);

        String userID = AdminDao.getUserID();
//        checkCommentPermission(userID);


        String title = event.getTitle();

        String workName = event.getWorkName();
        if ("null".equals(workName) || null == workName || "".equals(workName)) {
            workName = "";
        }else {
            workName = "\r\r(工作：" + workName + ")";
        }
        tv_bt.setText(title + workName);
        tv_content.setText(event.getContent());

        String address = event.getAddress();
        if (!"".equals(address) && !"null".equals(address) && null != address){
            tv_work.setText(address);
        }



        tv_start.setText("开始时间：" + event.getStartTime().split("T")[0]);
        tv_end.setText("截止时间：" + event.getEndTime().split("T")[0]);

        boolean complete = event.isFlag();
        if (complete) {
            iv_complete.setImageResource(R.mipmap.event_finish);
            tv_edit.setVisibility(View.GONE);
            fam_munu.setVisibility(View.GONE);
        } else {
            iv_complete.setImageResource(R.mipmap.event_doing);
        }
        tv_fuzeren.setText(event.getLeaderPerson());
        if (!event.getLeaderId().equals(userID)) {
            fab_complete.setVisibility(View.GONE);
            tv_edit.setVisibility(View.GONE);
        }
        GlideHelper.loadNet(civ_fuzeren, Globle.PHOTO_URL + event.getImage(), R.mipmap.image_weibo_home_2);
        String lable = event.getLable();
        ll_label.removeAllViews();
        if ("null".equals(lable) || "".equals(lable) || lable == null) {
            tv_label.setVisibility(View.GONE);
        } else {
            tv_label.setVisibility(View.GONE);
            String[] split = lable.split(" ");

            textViews = new TextView[split.length];
            for (int i = 0; i < split.length; i++) {


                TextView tv_lable = (TextView) LayoutInflater.from(EventDetailActivity.this).inflate(R.layout.label_show,ll_label, false);
                tv_lable.setText(split[i]);
                tv_lable.setBackgroundResource(R.drawable.shape_location_bg);
                tv_lable.setPadding(12, 12, 12, 12);
                ll_label.addView(tv_lable);//添加到父View
//                textView = new TextView(this);
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                layoutParams.gravity = Gravity.CENTER_VERTICAL;
//                layoutParams.leftMargin = 12;
//                textView.setLayoutParams(layoutParams);
//                textViews[i] = textView;
//                textView.setText(split[i]);
//                textView.setTextSize(12);
//                textView.setTextColor(Color.WHITE);
//                textView.setBackgroundResource(R.drawable.shape_location_bg);
//                textView.setPadding(17, 8, 17, 8);
//
//                ll_label.addView(textViews[i]);
            }
        }


        //事件参与人员
        // tv_canyu.setText(event.getAssignPersons());
        String imagePerson = event.getImagePerson();
        if ("null".equals(imagePerson) || imagePerson == null) {
            tv_assign.setText("暂无参与人员");
            ll_zhixingren.setEnabled(false);
        } else {
            String[] split = imagePerson.split(",");
            pl_member.removeAllViews();
            int member = split.length;
            if (member == 0) {
                for (int j = 0; j < event.getAssignPersons().split(",").length; j++) {
                    imageView = (CircleImageView) inflater.inflate(R.layout.fuzeren_head_praise, pl_member, false);
                    if (j < 3) {
                        GlideHelper.loadNet(imageView, " ", R.mipmap.image_weibo_home_2);
                        pl_member.addView(imageView);
                    } else {
                        iv_more.setVisibility(View.VISIBLE);
                    }

                }
                tv_assign.setText("共" + event.getAssignPersons().split(",").length + "人");
            } else {
                for (int j = 0; j < split.length; j++) {
                    imageView = (CircleImageView) inflater.inflate(R.layout.fuzeren_head_praise, pl_member, false);
                    if (j < 3) {
                        GlideHelper.loadNet(imageView, Globle.PHOTO_URL + split[j], R.mipmap.image_weibo_home_2);
                        pl_member.addView(imageView);
                    } else {
                        iv_more.setVisibility(View.VISIBLE);
                    }

                }
                tv_assign.setText("共" + member + "人");
            }
        }


        String upload1 = event.getUpload();
        photos.clear();
        records.clear();
        files.clear();
        if (!"".equals(upload1) && !"null".equals(upload1) && null != upload1) {
            String[] upload = upload1.split(",");
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


        String location = event.getLocation();
        if ("".equals(location) || "null".equals(location) || null == location) {
            iv_location.setVisibility(View.GONE);
        }
    }

    private void checkCommentPermission(String userID) {
        /**
         * 批示
         * 完全独立：    副总、总经理
         * 单独工作相关： 副总、总经理、工作负责人
         * 项目相关：    副总、总经理、工作负责人、项目负责人
         * */
        String wLeaderId = event.getwLeaderId();
        String pLeaderId = event.getpLeaderId();
        String role = AdminDao.getUser().getRole();
        Log.i(TAG, "initEventDetail wLeaderId: " + wLeaderId);
        if ("null".equals(wLeaderId) || "".equals(wLeaderId) || null == wLeaderId) {  //完全独立
            Log.i(TAG, "initEventDetail  事件完全独立: " + wLeaderId);
            if (role.equals(Globle.ROLE_FZ) || role.equals(Globle.ROLE_ZJL) || role.equals(Globle.ROLE_DUCHA)) {

                fabComment_Visible();
            } else {
                fam_Gone();
            }
        } else {
            if ("null".equals(pLeaderId) || "".equals(pLeaderId) || null == pLeaderId) {        //  单独工作相关
                Log.i(TAG, "initEventDetail  单独工作相关: " + pLeaderId);
                if (role.equals(Globle.ROLE_FZ) || role.equals(Globle.ROLE_ZJL) || role.equals(Globle.ROLE_DUCHA) || userID.equals(wLeaderId)) {
                    fabComment_Visible();
                } else {
                    fam_Gone();
                }
            } else {     //  项目相关
                Log.i(TAG, "initEventDetail  项目相关: " + pLeaderId);
                if (role.equals(Globle.ROLE_FZ) || role.equals(Globle.ROLE_ZJL) || role.equals(Globle.ROLE_DUCHA) || userID.equals(wLeaderId) || userID.equals(pLeaderId)) {    //项目相关
                    fabComment_Visible();
                } else {
                    fam_Gone();
                }
            }
        }
    }

    private void fabComment_Visible() {
        if (ll_comment.getVisibility() == View.VISIBLE) {
//            fab_comment.setVisibility(View.GONE);
//            fab_edit.setVisibility(View.VISIBLE);
            Log.i(TAG, "fabComment_Visible: " + "评论可见");
            String userID = AdminDao.getUserID();
            for (int i = 0; i < commentList.size(); i++) {
                addUserId = commentList.get(i).getAddUserId();
                Log.i(TAG, "fabComment_Visible  addUserId: " + addUserId);
                Log.i(TAG, "fabComment_Visible  userID: " + userID);
                if (addUserId.equals(userID)) {
                    fab_comment.setVisibility(View.GONE);
                    fab_edit.setVisibility(View.VISIBLE);
                    commentId = commentList.get(i).getId();
                } else {
                    fab_comment.setVisibility(View.VISIBLE);
                    fab_edit.setVisibility(View.GONE);
                }
            }
        } else {
            Log.i(TAG, "fabComment_Visible: " + "评论不可见");
            fab_comment.setVisibility(View.VISIBLE);
            fab_edit.setVisibility(View.GONE);
        }

    }

    private void fam_Gone() {
        Log.i(TAG, "fam_Gone: " + "不可见");
        fab_comment.setVisibility(View.GONE);
        String launchPerson = event.getLeaderId();
        String userID = AdminDao.getUserID();
        if (!userID.equals(launchPerson)) {
            fam_munu.setVisibility(View.GONE);
        } else {
            if (event.isFlag()) {
                fam_munu.setVisibility(View.GONE);
            }
        }

    }

    @OnClick({R.id.iv_event_detail_back, R.id.tv_event_detail_edit, R.id.iv_event_detail_location, R.id.btn_edit,
            R.id.ll_fuzeren, R.id.ll_zhixingren, R.id.iv_record, R.id.iv_file,
            R.id.fab_event_complete, R.id.fab_event_comment, R.id.fab_event_edit_comment})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_event_detail_back:
                finish();
                break;
            case R.id.tv_event_detail_edit:
                Intent edit = new Intent(this, CreateEventActivity.class);
                edit.putExtra("event", event);
                edit.putExtra("flag", 2);
                startActivityForResult(edit, Globle.EVENT_EDIT);

                break;
            case R.id.iv_event_detail_location:
                String location = event.getLocation();
                String substring = location.substring(6, location.length() - 1);
                String longitude = substring.split(",")[0];
                String latitude = substring.split(",")[1];
                Intent intent = new Intent(EventDetailActivity.this, BaseMapActivity.class);
                intent.putExtra("lat", Double.valueOf(latitude));
                intent.putExtra("long", Double.valueOf(longitude));
                intent.putExtra("map", "location_");
                intent.putExtra("type", "event");
                intent.putExtra("name", event.getTitle());
                startActivity(intent);
                break;
            case R.id.fab_event_complete:
                new MaterialDialog.Builder(EventDetailActivity.this)
                        .title("事件完成！")
                        .content("确定提交完成事件！")
                        .positiveText("确定")
                        .negativeText("取消")
                        .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which == DialogAction.POSITIVE) {

                                    eventPresenter.eventComplete(event.getId());
                                    dialog.dismiss();

                                } else if (which == DialogAction.NEGATIVE) {
                                    dialog.dismiss();
                                }
                            }
                        }).show();

                break;
            case R.id.fab_event_comment:
                new MaterialDialog.Builder(EventDetailActivity.this)
                        .title("事件批示！")
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
                                        if (flag == 4) {
                                            eventPresenter.createComment(alldata.getId(), input.toString(), AdminDao.getUserID(), "");
                                        } else if (flag == 5) {
                                            eventPresenter.createComment(marker_id, input.toString(), AdminDao.getUserID(), "");
                                        } else if (flag == 5) {
                                            eventPresenter.createComment(messageNotifyData.getId(), input.toString(), AdminDao.getUserID(), "");
                                        } else {
                                            eventPresenter.createComment(event.getId(), input.toString(), AdminDao.getUserID(), "");
                                        }


                                    }
                                }
                        )
                        .show();

                break;
            case R.id.fab_event_edit_comment:
                new MaterialDialog.Builder(EventDetailActivity.this)
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
                                        if (flag == 4) {
                                            //14b324a9-d023-4727-a578-abca1ac8a17a
                                            eventPresenter.createComment(alldata.getId(), input.toString(), AdminDao.getUserID(), commentId);
                                        } else if (flag == 5) {
                                            eventPresenter.createComment(marker_id, input.toString(), AdminDao.getUserID(), commentId);
                                        } else if (flag == 10) {
                                            eventPresenter.createComment(messageNotifyData.getId(), input.toString(), AdminDao.getUserID(), commentId);
                                        } else {
                                            eventPresenter.createComment(event.getId(), input.toString(), AdminDao.getUserID(), commentId);
                                        }


                                    }
                                }
                        )
                        .show();
                break;
            case R.id.ll_zhixingren:

                String assignPersons = event.getAssignPersons();
                String imagePerson = event.getImagePerson();
                if ("".equals(assignPersons) || "null".equals(assignPersons) || null == assignPersons) {
                    assignPersons = " ";
                }
                if ("".equals(imagePerson) || "null".equals(imagePerson) || null == imagePerson) {
                    imagePerson = " ";
                }
                String[] name = assignPersons.split(",");
                String[] image = imagePerson.split(",");
                List<String> list = new ArrayList<>();
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


                new MaterialDialog.Builder(this)
                        .items(list)
                        .adapter(new EventAssignAdapter(EventDetailActivity.this, list), new LinearLayoutManager(EventDetailActivity.this))
                        .show();

                break;
        }
    }


    @Override
    public void createEvent(String string) {
        Log.i(TAG, "createEvent: " + string);
        try {
            JSONObject jsonObject = new JSONObject(string);
            String result = jsonObject.getString("Result");
            String message = jsonObject.getString("Message");
            if ("成功！".equals(message)) {
                Toast.makeText(this, "事件提交完成", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "事件提交失败", Toast.LENGTH_SHORT).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getEventDetail(EventData.DataBean dataBean) {
        event = dataBean;
        Log.i(TAG, "getEventDetail: " + event);
        initEventDetail(EventDetailActivity.this);
    }

    @Override
    public void getEventComment(String string) {
        Log.i(TAG, "getEventComment: " + string);
        if ("".equals(string)) {
            ll_comment.setVisibility(View.GONE);

            checkCommentPermission(AdminDao.getUserID());

        } else {
            ll_comment.setVisibility(View.VISIBLE);
            commentList.clear();

            EventComment eventComment = new Gson().fromJson(string, new TypeToken<EventComment>() {
            }.getType());

            List<EventComment.DataBean> data = eventComment.getData();
            commentList.addAll(data);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EventDetailActivity.this);
            rv_comment.setLayoutManager(linearLayoutManager);


            String userID = AdminDao.getUserID();
            for (int i = 0; i < commentList.size(); i++) {
                addUserId = commentList.get(i).getAddUserId();
                if (string.contains(userID)) {
                    fab_comment.setVisibility(View.GONE);
                    fab_edit.setVisibility(View.VISIBLE);
                    commentId = commentList.get(i).getId();
                } else {
                    fab_comment.setVisibility(View.VISIBLE);
                    fab_edit.setVisibility(View.GONE);
                }
            }
            String eventId;
            if (flag == 4) {
                eventId = alldata.getId();
            } else if (flag == 5) {
                eventId = marker_id;
            } else if (flag == 10) {
                eventId = messageNotifyData.getId();
            } else {
                eventId = event.getId();
            }
            commentAdapter = new EventCommentAdapter(EventDetailActivity.this, commentList, eventId);
            rv_comment.setAdapter(commentAdapter);
        }
    }

    @Override
    public void postComment(String string) {
        Log.i(TAG, "postComment: " + string);

        if ("评论成功".equals(string)) {
            ToastUtils.showToast(EventDetailActivity.this, "批示成功~~~");
            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Globle.EVENT_EDIT) {
                if (flag == 4) {
                    eventPresenter.getEventDetail(alldata.getId());
                    eventPresenter.getEventComment(alldata.getId());
                } else if (flag == 5) {
                    eventPresenter.getEventDetail(marker_id);
                    eventPresenter.getEventComment(marker_id);
                } else if (flag == 10) {
                    eventPresenter.getEventDetail(messageNotifyData.getId());
                    eventPresenter.getEventComment(messageNotifyData.getId());
                } else {
                    eventPresenter.getEventDetail(event.getId());
                    eventPresenter.getEventComment(event.getId());
                }
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();


        if (flag == 4) {
            eventPresenter.getEventDetail(alldata.getId());
            eventPresenter.getEventComment(alldata.getId());
        } else if (flag == 5) {
            eventPresenter.getEventDetail(marker_id);
            eventPresenter.getEventComment(marker_id);
        } else if (flag == 10) {
            eventPresenter.getEventDetail(messageNotifyData.getId());
            eventPresenter.getEventComment(messageNotifyData.getId());
        } else {
            eventPresenter.getEventComment(event.getId());
        }


    }

}
