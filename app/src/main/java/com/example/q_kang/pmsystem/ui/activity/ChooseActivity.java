package com.example.q_kang.pmsystem.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseActivity;
import com.example.q_kang.pmsystem.modules.bean.bean.ChooseData;
import com.example.q_kang.pmsystem.present.im.ChooseFramePresent;
import com.example.q_kang.pmsystem.ui.adpter.project.ChooseAdapter;
import com.example.q_kang.pmsystem.view.ChooseFrameView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseActivity extends BaseActivity implements ChooseFrameView {

    @BindView(R.id.ll_mycollection_bottom_dialog)
    LinearLayout ll_select;
    @BindView(R.id.recyclerview_choose)
    RecyclerView recyclerview_choose;
    @BindView(R.id.select_all)
    TextView select_all;
    @BindView(R.id.btn_complete)
    TextView btn_complete;
    @BindView(R.id.tv)
    TextView tv_text;
    @BindView(R.id.tv_select_num)
    TextView tv_select_num;
    @BindView(R.id.iv_choose_back)
    ImageView ivBack;
    @BindView(R.id.et_choose_search)
    EditText etChoose;
    @BindView(R.id.tv_choose_frame)
    TextView tvFrame;

    private ChooseFramePresent chooseFramePresent;

    private ChooseAdapter chooseAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<ChooseData> mList;

    private List<ChooseData> chooseDataList;
    private List<ChooseData> allDatalist = new ArrayList<>();

    private boolean isSelectAll = false;
    private int index;

    private String flag;
    private int returnIndex;

//    private List<Integer> indexList = new ArrayList<>();
    private List<String> indexList = new ArrayList<>();


    @Override
    public int bindLayout() {
        return R.layout.activity_choose;
    }

    @Override
    public void doBusiness(Context mContext) {
        chooseFramePresent = new ChooseFramePresent(this);

        flag = getIntent().getStringExtra("flag");

        returnIndex = getIntent().getIntExtra("index", 0);
        mList = new ArrayList<>();
        chooseDataList = new ArrayList<>();
        mLinearLayoutManager = new LinearLayoutManager(this);
        recyclerview_choose.setLayoutManager(mLinearLayoutManager);
        chooseAdapter = new ChooseAdapter(mContext, mList);
        recyclerview_choose.setAdapter(chooseAdapter);

        chooseFramePresent.getFrame();
        switch (flag) {
            case "fuzeren"://单选
                tv_select_num.setVisibility(View.GONE);
                select_all.setVisibility(View.GONE);
                tv_text.setVisibility(View.GONE);
                chooseAdapter.setOnItemClickListener(new ChooseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(int position, List<ChooseData> datas) {
                        chooseDataList.removeAll(chooseDataList);
                        for (ChooseData data : datas) {
                            data.setSelect(false);
                        }
                        ChooseData chooseData = datas.get(position);
                        chooseData.setSelect(true);
                        btn_complete.setEnabled(true);
                        setBtnBackGround(1);
                        chooseAdapter.notifyDataSetChanged();

                        chooseDataList.add(chooseData);
                        Log.i("fuzeren", "onItemClickListener: " + chooseDataList);
                    }
                });

                break;
            case "member"://多选

                selectMembers();
                break;
        }

        searchPerson(mContext);//模糊匹配

    }

    @OnClick({R.id.select_all, R.id.btn_complete, R.id.iv_choose_back, R.id.tv_choose_frame})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.select_all:
                selectAllMain();
                break;
            case R.id.btn_complete:
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("choose", (ArrayList<? extends Parcelable>) chooseDataList);
                intent.putStringArrayListExtra("indexList", (ArrayList<String>) indexList);
//                Log.i("fuzeren", "onViewClick: " + chooseDataList);
                intent.putExtra("index", returnIndex);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.iv_choose_back:
                finish();
                break;
//            case R.id.tv_choose_frame:
//                startActivity(FrameFragmentActivity.class);
//                break;
        }
    }

    private void searchPerson(Context mContext) {

        etChoose.addTextChangedListener(new TextWatcher() {
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
                    mList = new ArrayList<>();
                    mList = allDatalist;
                    chooseAdapter.notifyAdapter(mList, false);
                } else {
                    tvFrame.setVisibility(View.GONE);
                    mList = new ArrayList<>();
                    String inputStr = etChoose.getText().toString();
                    for (int i = 0; i < allDatalist.size(); i++) {
                        if (allDatalist.get(i).getFrame().getRealName().contains(inputStr)
                                || allDatalist.get(i).getFrame().getDepartName().contains(inputStr)) {
                            mList.add(allDatalist.get(i));
                        }
                    }
                    chooseAdapter.notifyAdapter(mList, false);
                }
            }
        });
    }

    private void selectMembers() {


        chooseAdapter.setOnItemClickListener(new ChooseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, List<ChooseData> datas) {
                ChooseData chooseData = datas.get(position);
                Log.i(TAG, "onItemClickListener: " + chooseData);
                boolean select = chooseData.isSelect();
                if (!select) {
                    index++;
                    chooseData.setSelect(true);
                    chooseDataList.add(chooseData);
//                    indexList.add(position);
                    indexList.add(chooseData.getFrame().getId());

                    if (index == datas.size()) {
                        isSelectAll = true;
                        select_all.setText("取消全选");
                    }
                } else {
                    chooseData.setSelect(false);
                    chooseDataList.remove(chooseData);
                    for (int i = 0; i < indexList.size(); i++) {
                        if ((chooseData.getFrame().getId().trim()).equals(indexList.get(i).trim()) ){
                            indexList.remove(i);
                        }
                    }


                    index--;
                    isSelectAll = false;
                    select_all.setText("全选");
                }


                Log.i(TAG, "doBusiness indx选中: " + indexList.size() + indexList);
                setBtnBackGround(index);
                tv_select_num.setText(String.valueOf(index));
                chooseAdapter.notifyDataSetChanged();

            }
        });


    }

    private void selectAllMain() {
        if (chooseAdapter == null) return;
        if (!isSelectAll) {
            for (int i = 0, j = chooseAdapter.getDatas().size(); i < j; i++) {
                chooseAdapter.getDatas().get(i).setSelect(true);

            }
            index = chooseAdapter.getDatas().size();
            btn_complete.setEnabled(true);
            select_all.setText("取消全选");
            isSelectAll = true;
        } else {
            for (int i = 0, j = chooseAdapter.getDatas().size(); i < j; i++) {
                chooseAdapter.getDatas().get(i).setSelect(false);
            }
            index = 0;
            btn_complete.setEnabled(false);
            select_all.setText("全选");
            isSelectAll = false;
        }
        chooseAdapter.notifyDataSetChanged();
        setBtnBackGround(index);
        tv_select_num.setText(String.valueOf(index));
    }

    @SuppressLint("ResourceAsColor")
    private void setBtnBackGround(int size) {
        if (size != 0) {
            btn_complete.setBackgroundColor(Color.parseColor("#2a7fd7"));
//            btn_delete.setBackgroundResource(R.drawable.button_shape);
            btn_complete.setEnabled(true);
            btn_complete.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else {
//            btn_delete.setBackgroundResource(R.drawable.button_noclickable_shape);
            btn_complete.setBackgroundColor(Color.parseColor("#888888"));
            btn_complete.setEnabled(false);
            btn_complete.setTextColor(ContextCompat.getColor(this, R.color.colorHui));
        }
    }

    @Override
    public void setChooseAdapter(List<ChooseData> datas) {
        Log.i(TAG, "setChooseAdapter datas: " + datas);
        mList = datas;
        allDatalist = datas;
        chooseAdapter.notifyAdapter(mList, false);

        if ("member".equals(flag)){
//            indexList = getIntent().getIntegerArrayListExtra("indexList");
            indexList = getIntent().getStringArrayListExtra("indexList");
            Log.i(TAG, "doBusiness index得到集合: " + indexList);

            index = indexList.size();
            for (int i = 0; i < allDatalist.size(); i++) {
                for (int j = 0; j < index; j++) {
                    String s = indexList.get(j);
                    Log.i(TAG, "setChooseAdapter  选中以后: " + s.trim());
                    Log.i(TAG, "setChooseAdapter 组织架构: " + allDatalist.get(i).getFrame().getId().trim());
                    if (s.trim().equals(allDatalist.get(i).getFrame().getId().trim())){
                        ChooseData chooseData = allDatalist.get(i);
                        chooseData.setSelect(true);
                        chooseDataList.add(chooseData);
                    }
                }
            }


//            for (int i = 0; i < allDatalist.size(); i++) {
//                for (int j = 0; j < index; j++) {
//                    int integer = indexList.get(j);
//                    if (i == integer){
//                        ChooseData chooseData = allDatalist.get(integer);
//                        chooseData.setSelect(true);
//                        chooseDataList.add(chooseData);
//                    }
//                }
//            }

            setBtnBackGround(index);
            tv_select_num.setText(String.valueOf(index));
            chooseAdapter.notifyDataSetChanged();
        }


    }
}
