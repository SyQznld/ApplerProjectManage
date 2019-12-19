package com.example.q_kang.pmsystem.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.q_kang.pmsystem.JGuang.ExampleUtil;
import com.example.q_kang.pmsystem.JGuang.LocalBroadcastManager;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.dao.AdminDao;
import com.example.q_kang.pmsystem.modules.bean.bean.UserBean;
import com.example.q_kang.pmsystem.present.im.LoginPresent;
import com.example.q_kang.pmsystem.view.LoginView;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.scwang.wave.MultiWaveHeader;

import java.util.List;
import java.util.Set;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;


public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_forget_pass)
    TextView tvForgetPass;
    @BindView(R.id.waveHeader)
    MultiWaveHeader waveHeader;

    private LoginPresent loginPresent;
    private Button button;
    private String rid;

    public static boolean isForeground = false;

    @Override
    public int bindLayout() {
        if (AdminDao.getUser() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }


//        registerMessageReceiver();


        JPushInterface.cleanTags(this, 0);
        JPushInterface.deleteAlias(this, 0);

        rid = JPushInterface.getRegistrationID(getApplicationContext());
        Log.i(TAG, "bindLayout: " + rid);

        return R.layout.activity_login;
    }

    @Override
    public void doBusiness(Context mContext) {

        XXPermissions.with(LoginActivity.this)
//                .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
//                .permission(Permission.REQUEST_INSTALL_PACKAGES, Permission.SYSTEM_ALERT_WINDOW) //支持请求安装权限和悬浮窗权限
                .permission(Permission.Group.STORAGE) //支持多个权限组进行请求，不指定则默以清单文件中的危险权限进行请求
                .permission(Permission.Group.CALENDAR)
                .permission(Permission.Group.CAMERA)
                .permission(Permission.Group.LOCATION)
                .permission(Permission.Group.PHONE)
                .permission(Permission.Group.SENSORS)
                .permission(Permission.Group.MICROPHONE)
                .permission(Permission.Group.SMS)
                .permission(Permission.SYSTEM_ALERT_WINDOW)
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
//                            Toast.makeText(LoginActivity.this, "获取权限成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "获取权限成功，部分权限未正常授予", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            Toast.makeText(LoginActivity.this, "被永久拒绝授权，请手动授予权限", Toast.LENGTH_SHORT).show();
                            //如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.gotoPermissionSettings(LoginActivity.this);
                        } else {
//                            Toast.makeText(LoginActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//        if (
//                !XXPermissions.isHasPermission(this, Permission.Group.STORAGE) ||
//                        !XXPermissions.isHasPermission(this, Permission.Group.CALENDAR) ||
//                        !XXPermissions.isHasPermission(this, Permission.Group.CAMERA) ||
//                        !XXPermissions.isHasPermission(this, Permission.Group.LOCATION) ||
//                        !XXPermissions.isHasPermission(this, Permission.Group.PHONE) ||
//                        !XXPermissions.isHasPermission(this, Permission.Group.SENSORS) ||
//                        !XXPermissions.isHasPermission(this, Permission.Group.MICROPHONE) ||
//                        !XXPermissions.isHasPermission(this, Permission.Group.SMS) ||
//                        !XXPermissions.isHasPermission(this, Permission.SYSTEM_ALERT_WINDOW)
//
//                ) {
//            XXPermissions.gotoPermissionSettings(this);
//        }


        loginPresent = new LoginPresent(this);
        loginPresent.attachView(LoginActivity.this);
        button = findViewById(R.id.btn_login);
        onClick();

    }

    private void onClick() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: " + rid);

                loginPresent.getResult(
                        etUsername.getText().toString(),
                        etPassword.getText().toString(),
                        "是",
                        rid);
            }
        });


        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public void onSuccess(UserBean user) {
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
        Log.i("aaa", "initTagAndAlias Tags: " + inPutTags);
        Log.i("aaa", "initTagAndAlias Alias: " + inPutAlias);

        if (inPutTags != null) {
            JPushInterface.setTags(LoginActivity.this, 0, inPutTags);
        }
        if (inPutAlias != null) {
            JPushInterface.setAlias(LoginActivity.this, 0, inPutAlias);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.class);
            }
        }, 1000);
    }


    public void requestPermission(View view) {
        XXPermissions.with(this)
                //.constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                //.permission(Permission.REQUEST_INSTALL_PACKAGES, Permission.SYSTEM_ALERT_WINDOW) //支持请求安装权限和悬浮窗权限
                .permission(Permission.Group.STORAGE, Permission.Group.CAMERA)
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            Toast.makeText(LoginActivity.this, "获取权限成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "获取权限成功，部分权限未正常授予", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            Toast.makeText(LoginActivity.this, "被永久拒绝授权，请手动授予权限", Toast.LENGTH_SHORT).show();
                            //如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.gotoPermissionSettings(LoginActivity.this);
                        } else {
                            Toast.makeText(LoginActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void isHasPermission(View view) {
        if (XXPermissions.isHasPermission(LoginActivity.this, Permission.Group.STORAGE, Permission.Group.CAMERA)) {
            Toast.makeText(LoginActivity.this, "已经获取到权限，不需要再次申请了", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginActivity.this, "还没有获取到权限或者部分权限未授予", Toast.LENGTH_SHORT).show();
        }
    }

    public void gotoPermissionSettings(View view) {
        XXPermissions.gotoPermissionSettings(LoginActivity.this);
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
                    long l = System.currentTimeMillis();
//                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e) {
            }
        }
    }


    @Override
    public void onFailure() {

    }

    @Override
    public void onError() {

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
