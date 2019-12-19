package com.example.q_kang.pmsystem.ui.view.Utils;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.q_kang.pmsystem.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TitleView extends LinearLayout {
    private LayoutInflater mInflater;
    private Context mContext;
    @BindView(R.id.pie_piechart)
    PieChart pieChart;

    public TitleView(Context context) {
        super(context);
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);

    }

    public void getView(ListView listView) {
        View view = mInflater.inflate(R.layout.view_picture, this);
        ButterKnife.bind(this);
        listView.addHeaderView(view);
        pesc();
    }

    private void pesc() {
        Description description = new Description();
        description.setTextColor(Color.parseColor("#FF2275EC"));
        description.setTextSize(12f);
        description.setText("单位“%”");
        pieChart.setDescription(description);
        pieChart.setUsePercentValues(true);
        pieChart.setExtraOffsets(5, 5, 5, 5);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleColor(Color.parseColor("#8f000000"));
        pieChart.setTransparentCircleAlpha(50);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                float y = e.getY();
                Toast.makeText(mContext, String.valueOf(y), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        pieChart.setHoleRadius(80f); //半径
        pieChart.setTransparentCircleRadius(78f);// 半透明圈
        pieChart.setDrawCenterText(true);//饼状图中间可以添加文字
        // 如果没有数据的时候，会显示这个，类似ListView的EmptyView
        pieChart.setNoDataText("");
        pieChart.setUsePercentValues(true);//设置显示成比例
//        pieChart.setCenterText("完成度");
        pieChart.setRotationAngle(-90); // 初始旋转角度
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true); // 可以手动旋转
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutQuad); //设置动画
        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);  //左下边显示
        mLegend.setFormSize(12f);//比例块字体大小
        mLegend.setXEntrySpace(2f);//设置距离饼图的距离，防止与饼图重合
        mLegend.setYEntrySpace(2f);
        mLegend.setWordWrapEnabled(true);
        mLegend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        mLegend.setTextColor(Color.RED);
        List<LegendEntry> entriess = new ArrayList<>();
        LegendEntry entry4 = new LegendEntry();
        entry4.label = "已完成";
        entry4.form = Legend.LegendForm.CIRCLE;
        entry4.formColor = Color.parseColor("#FF2a7fd7");
        LegendEntry entry1 = new LegendEntry();
        entry1.form = Legend.LegendForm.CIRCLE;
        entry1.label = "未完成";
        entry1.formColor = Color.parseColor("#FFeeeeee");
        entriess.add(entry4);
        entriess.add(entry1);
        mLegend.setCustom(entriess);
        List<PieEntry> entries = new ArrayList<>();
        PieEntry entrys = new PieEntry(80);
        PieEntry entry = new PieEntry(20);

        pieChart.setCenterText("完成度" + entry.getValue() + "%");
        entries.add(entry);
        entries.add(entrys);
        setData((ArrayList<PieEntry>) entries, pieChart);
    }

    private void setData(ArrayList<PieEntry> counts, PieChart pieChart) {
        PieDataSet dataSet = new PieDataSet(counts, "");
//        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(4f);
        int[] mColors = {0xFF2a7fd7, 0xFFeeeeee, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
                0xFFE6B800, 0xFF7CFC00};
        ArrayList<Integer> colors = new ArrayList<>();

        for (int i = 0; i < mColors.length; i++) {
            colors.add(mColors[i]);
        }
        dataSet.setColors(colors);
        PieData data = new PieData();
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(14f);
        data.setDataSet(dataSet);
        data.setValueTextColor(getResources().getColor(R.color.transparent));
        pieChart.setData(data);
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

}
