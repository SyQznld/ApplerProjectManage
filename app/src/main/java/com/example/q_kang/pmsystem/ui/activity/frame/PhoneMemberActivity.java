package com.example.q_kang.pmsystem.ui.activity.frame;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.modules.bean.bean.Person;
import com.example.q_kang.pmsystem.ui.adpter.PhoneAdapter;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PhoneMemberActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 获取库Phone表字段
     **/
    private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Photo.PHOTO_ID,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID};
    /**
     * 联系人显示名称
     **/
    private static final int PHONES_DISPLAY_NAME = 0;

    /**
     * 电话号码
     **/
    private static final int PHONES_NUMBER = 1;

    /**
     * 头像ID
     **/
    private static final int PHONES_PHOTO_ID = 2;

    /**
     * 联系人的ID
     **/
    private static final int PHONES_CONTACT_ID = 3;

    private List<Person> members;
    private PhoneAdapter peopleAdapter;
    private SuspensionDecoration mDecoration;

    @BindView(R.id.et_phone_search)
    EditText et_member_search;
    @BindView(R.id.recy_phone_member)
    RecyclerView recy_phone_member;
    @BindView(R.id.iv_phone_back)
    ImageView iv_phone_back;
    @BindView(R.id.mIndexBar)
    IndexBar mIndexBar;
    @BindView(R.id.tvSideBarHint)
    TextView tvSideBarHint;
    private LinearLayoutManager mManager;

    private List<Person> searchMembers;


    @Override
    public int bindLayout() {
        return R.layout.activity_phone_member;
    }

    @Override
    public void doBusiness(Context mContext) {
        getPhoneContacts();
        peopleAdapter = new PhoneAdapter(members);
        Log.i(TAG, "doBusiness: " + members);
        recy_phone_member.setHasFixedSize(true);
        recy_phone_member.setLayoutManager(mManager = new LinearLayoutManager(this));
        recy_phone_member.setAdapter(peopleAdapter);
        recy_phone_member.addItemDecoration(mDecoration = new SuspensionDecoration(this, members));


        mIndexBar.setmPressedShowTextView(tvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager

        mIndexBar.setmSourceDatas(members)//设置数据
                .invalidate();
        mDecoration.setmDatas(members);
        iv_phone_back.setOnClickListener(this);

        et_member_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String editable = s.toString();
                if ("".equals(editable) || null == editable) {

                    peopleAdapter = null;
                    recy_phone_member.setAdapter(new PhoneAdapter(members));

                } else {
                    searchMembers = new ArrayList<>();
                    String inputStr = et_member_search.getText().toString();
                    for (int i = 0; i < members.size(); i++) {
                        if (members.get(i).getName().contains(inputStr) || members.get(i).getPhone().contains(inputStr)) {
                            searchMembers.add(members.get(i));
                        }
                    }
                    Log.i(TAG, "afterTextChanged: " + searchMembers);
                    if (null != peopleAdapter) {
                        peopleAdapter = null;
                    }
                    recy_phone_member.setAdapter(new PhoneAdapter(searchMembers));

                }
            }
        });
    }

    // 获取手机联系人
    private void getPhoneContacts() {
        members = new ArrayList<>();
        // rely=(RelativeLayout) findViewById(R.id.relationId);
        ContentResolver resolver = this.getContentResolver();

        // 获取手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PHONES_PROJECTION, null, null, null);

        // 不为空
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                Person person = new Person();
                // 得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER);
                // 当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                // 得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME);
                // 得到联系人ID
                Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID);
                // 得到联系人头像ID
                Long imgid = phoneCursor.getLong(PHONES_PHOTO_ID);
                // 得到联系人头像Bitamp
                Bitmap bitmap = null;
                // photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
                if (imgid > 0) {
                    Uri uri = ContentUris.withAppendedId(
                            ContactsContract.Contacts.CONTENT_URI, contactid);
                    InputStream input = ContactsContract.Contacts
                            .openContactPhotoInputStream(resolver, uri);
                    bitmap = BitmapFactory.decodeStream(input);
                } else {
                    // 设置默认
                    bitmap = BitmapFactory.decodeResource(getResources(),
                            R.mipmap.bottom_mine);
                }
                person.setName(contactName);
                person.setPhone(phoneNumber);
                person.setIamge(bitmap);
                members.add(person);
            }
            phoneCursor.close();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_phone_back:
                finish();
                break;
        }
    }
}
