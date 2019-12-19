package com.example.q_kang.pmsystem.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.base.BaseFragment;
import com.example.q_kang.pmsystem.dao.AdminDao;
import com.example.q_kang.pmsystem.modules.bean.bean.MessageNotifyData;
import com.example.q_kang.pmsystem.present.im.MessageListPresenter;
import com.example.q_kang.pmsystem.ui.activity.FileActivity;
import com.example.q_kang.pmsystem.ui.activity.ModelActivity;
import com.example.q_kang.pmsystem.ui.activity.schedule.ScheduleActivity;
import com.example.q_kang.pmsystem.ui.activity.document.DocumentActivity;
import com.example.q_kang.pmsystem.ui.activity.document.DocumentDetailActivity;
import com.example.q_kang.pmsystem.ui.activity.event.EventDetailActivity;
import com.example.q_kang.pmsystem.ui.activity.event.EventListActivity;
import com.example.q_kang.pmsystem.ui.activity.map.BaseMapActivity;
import com.example.q_kang.pmsystem.ui.activity.news.NewsActivity;
import com.example.q_kang.pmsystem.ui.activity.news.NewsDetailActivity;
import com.example.q_kang.pmsystem.ui.activity.photo.PhotoAlbumActivity;
import com.example.q_kang.pmsystem.ui.activity.project.ProjectActivity;
import com.example.q_kang.pmsystem.ui.activity.project.ProjectDetailActivity;
import com.example.q_kang.pmsystem.ui.activity.work.WorkDetailActivity;
import com.example.q_kang.pmsystem.ui.activity.work.WorkListActivity;
import com.example.q_kang.pmsystem.ui.view.Utils.view_.MessageNoticeMF;
import com.example.q_kang.pmsystem.view.MessageListView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.gongwen.marqueen.MarqueeFactory;
import com.gongwen.marqueen.MarqueeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends BaseFragment implements MessageListView {
    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;

//    @BindView(R.id.piechart)
//    PieChart pieChart;
    @BindView(R.id.tv_news)
    TextView tvNews;
    @BindView(R.id.tv_project)
    TextView tvProject;
    @BindView(R.id.tv_work)
    TextView tvWork;
    @BindView(R.id.tv_event)
    TextView tvEvent;
    @BindView(R.id.tv_photo)
    TextView tvPhoto;
    @BindView(R.id.tv_model)
    TextView tvModel;
    @BindView(R.id.tv_file)
    TextView tvFile;
    @BindView(R.id.tv_schedule)
    TextView tvSchedule;
    @BindView(R.id.tv_map)
    TextView tvMap;
    @BindView(R.id.tv_document)
    TextView tvDocument;
    Unbinder unbinder1;

    private MessageListPresenter messageListPresenter;

    @Override
    public int setFragView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initFragView() {
    }

    @Override
    protected void doFragBusiness() {

        messageListPresenter = new MessageListPresenter(this);
//        messageListPresenter.getMessageList(AdminDao.getUserID());


//        //饼状图
//        pesc();

    }

//    private void pesc() {
//        Description description = new Description();
//        description.setTextColor(Color.parseColor("#FF2275EC"));
//        description.setText("单位“%”");
//        pieChart.setDescription(description);
//        pieChart.setUsePercentValues(true);
//        pieChart.setExtraOffsets(5, 10, 5, 5);
//        pieChart.setDrawHoleEnabled(true);
//        pieChart.setTransparentCircleColor(Color.BLUE);
//        pieChart.setTransparentCircleAlpha(110);
//        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//            @Override
//            public void onValueSelected(Entry e, Highlight h) {
//
//            }
//
//            @Override
//            public void onNothingSelected() {
//
//            }
//        });
//        pieChart.setHoleRadius(45f); //半径
//        pieChart.setTransparentCircleRadius(48f);// 半透明圈
//        pieChart.setDrawCenterText(true);//饼状图中间可以添加文字
//        // 如果没有数据的时候，会显示这个，类似ListView的EmptyView
//        pieChart.setNoDataText("");
//        pieChart.setUsePercentValues(true);//设置显示成比例
//        pieChart.setCenterText("完成度");
//        pieChart.setRotationAngle(0); // 初始旋转角度
//        // enable rotation of the chart by touch
//        pieChart.setRotationEnabled(true); // 可以手动旋转
//        pieChart.setHighlightPerTapEnabled(true);
//        pieChart.animateY(3000, Easing.EasingOption.EaseInOutQuad); //设置动画
//        Legend mLegend = pieChart.getLegend();  //设置比例图
//        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);  //左下边显示
//        mLegend.setFormSize(12f);//比例块字体大小
//        mLegend.setXEntrySpace(2f);//设置距离饼图的距离，防止与饼图重合
//        mLegend.setYEntrySpace(2f);
//        mLegend.setWordWrapEnabled(true);
//        mLegend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
//        mLegend.setTextColor(Color.RED);
//        List<LegendEntry> entriess = new ArrayList<>();
//        LegendEntry entry4 = new LegendEntry();
//        entry4.label = "项目进度";
//        entry4.form = Legend.LegendForm.CIRCLE;
//        entry4.formColor = Color.parseColor("#FFD9508A");
//        LegendEntry entry1 = new LegendEntry();
//        entry1.form = Legend.LegendForm.CIRCLE;
//        entry1.label = "未完成";
//        entry1.formColor = Color.parseColor("#FFFC9406");
//        entriess.add(entry4);
//        entriess.add(entry1);
//        mLegend.setCustom(entriess);
//        List<PieEntry> entries = new ArrayList<>();
//        PieEntry entrys = new PieEntry(50);
//        PieEntry entry = new PieEntry(50);
//
//        entries.add(entry);
//        entries.add(entrys);
//        setData((ArrayList<PieEntry>) entries, pieChart);
//    }

    private void setData(ArrayList<PieEntry> counts, PieChart pieChart) {
        PieDataSet dataSet = new PieDataSet(counts, "");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
//
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData();
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(12f);
        data.setDataSet(dataSet);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

    @OnClick({R.id.tv_news, R.id.tv_project, R.id.tv_work, R.id.tv_event,
            R.id.tv_model, R.id.tv_photo, R.id.tv_file, R.id.tv_schedule, R.id.tv_map,
            R.id.tv_document})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_news:
                startActivity(new Intent(activity, NewsActivity.class));
                break;
            case R.id.tv_project:
                startActivity(new Intent(activity, ProjectActivity.class));
                break;
            case R.id.tv_work:
                startActivity(new Intent(activity, WorkListActivity.class));
                break;
            case R.id.tv_event:
                startActivity(new Intent(activity, EventListActivity.class));
                break;
            case R.id.tv_photo:
                startActivity(new Intent(activity, PhotoAlbumActivity.class));
                break;
            case R.id.tv_model:
                startActivity(new Intent(activity, ModelActivity.class));
                break;
            case R.id.tv_file:
                Intent fileIntent = new Intent(activity, FileActivity.class);
                fileIntent.putExtra("choose", false);//是否选中
                activity.startActivity(fileIntent);
                break;
            case R.id.tv_schedule:
                startActivity(new Intent(activity, ScheduleActivity.class));
                break;
            case R.id.tv_map:
                startActivity(new Intent(activity, BaseMapActivity.class));
                break;
            case R.id.tv_document:
                startActivity(new Intent(activity, DocumentActivity.class));
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }


    @Override
    public void onResume() {
        super.onResume();
        messageListPresenter.getMessageList(AdminDao.getUserID());
    }

    @Override
    public void getMessageList(String string) {
        Log.i("", "getMessageList: " + string);

        //跑马灯
        initMarqueeView(string);
    }

    @Override
    public void deleteAllMessage(String string) {

    }

    /**
     * 跑马灯轮播显示消息通知 点击跳转
     * */
    private void initMarqueeView(String string) {
        MessageNotifyData messageNotifyData = new Gson().fromJson(string, new TypeToken<MessageNotifyData>() {
        }.getType());
        List<MessageNotifyData.DataBean> data = messageNotifyData.getData();


        MarqueeFactory<TextView, MessageNotifyData.DataBean> marqueeFactory = new MessageNoticeMF(activity);

        //开始翻转
        marqueeView.startFlipping();
        marqueeFactory.setOnItemClickListener(new MarqueeFactory.OnItemClickListener<TextView, MessageNotifyData.DataBean>() {
            @Override
            public void onItemClickListener(MarqueeFactory.ViewHolder<TextView, MessageNotifyData.DataBean> holder) {
                MessageNotifyData.DataBean dataBean = holder.data;
                int type = Integer.parseInt(dataBean.get_Type());

                Intent intent = null;

                if (type == 0) {
                    intent = new Intent(getActivity(), ProjectDetailActivity.class);
                } else if (type == 1) {
                    intent = new Intent(getActivity(), WorkDetailActivity.class);
                } else if (type == 2) {
                    intent = new Intent(getActivity(), EventDetailActivity.class);
                } else if (type == 3) {
                    intent = new Intent(getActivity(), DocumentDetailActivity.class);
                } else if (type == 4) {
                    intent = new Intent(getActivity(), NewsDetailActivity.class);
                }

                intent.putExtra("messagenotify", dataBean);
                intent.putExtra("flag", 10);
                getActivity().startActivity(intent);

            }
        });

        marqueeFactory.setData(data);
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeView.startFlipping();
    }
}
