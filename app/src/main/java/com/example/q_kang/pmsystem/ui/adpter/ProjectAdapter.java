package com.example.q_kang.pmsystem.ui.adpter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.ui.activity.project.ProjectCheckActivity;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {
    private Context context;
    private List<String> datas;

    public ProjectAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.project_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.projectName.setText(datas.get(holder.getAdapterPosition()));
        pesc(holder.piechart);
        holder.tv_projectManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProjectCheckActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.piechart)
        PieChart piechart;
        @BindView(R.id.projectName)
        TextView projectName;
        @BindView(R.id.tv_projectManage)
        TextView tv_projectManage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    private void pesc(PieChart pieChart) {
        Description description = new Description();
        description.setTextColor(Color.parseColor("#FF2275EC"));
        description.setText("单位“%”");
        pieChart.setDescription(description);
        pieChart.setUsePercentValues(true);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleColor(Color.BLUE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });
        pieChart.setHoleRadius(45f); //半径
        pieChart.setTransparentCircleRadius(48f);// 半透明圈
        pieChart.setDrawCenterText(true);//饼状图中间可以添加文字
        // 如果没有数据的时候，会显示这个，类似ListView的EmptyView
        pieChart.setNoDataText("");
        pieChart.setUsePercentValues(true);//设置显示成比例
        pieChart.setCenterText("完成度");
        pieChart.setRotationAngle(0); // 初始旋转角度
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true); // 可以手动旋转
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.animateY(3000, Easing.EasingOption.EaseInOutQuad); //设置动画
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
        entry4.label = "项目进度";
        entry4.form = Legend.LegendForm.CIRCLE;
        entry4.formColor = Color.parseColor("#FFD9508A");
        LegendEntry entry1 = new LegendEntry();
        entry1.form = Legend.LegendForm.CIRCLE;
        entry1.label = "未完成";
        entry1.formColor = Color.parseColor("#FFFC9406");
        entriess.add(entry4);
        entriess.add(entry1);
        mLegend.setCustom(entriess);
        List<PieEntry> entries = new ArrayList<>();
        PieEntry entrys = new PieEntry(50);
        PieEntry entry = new PieEntry(50);

        entries.add(entry);
        entries.add(entrys);
        setData((ArrayList<PieEntry>) entries, pieChart);
    }

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

}
