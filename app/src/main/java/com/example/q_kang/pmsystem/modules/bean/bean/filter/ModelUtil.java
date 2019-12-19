package com.example.q_kang.pmsystem.modules.bean.bean.filter;

import com.example.q_kang.pmsystem.R;
import com.example.q_kang.pmsystem.modules.bean.bean.EventModelData;
import com.example.q_kang.pmsystem.modules.bean.bean.ModelData;
import com.example.q_kang.pmsystem.modules.bean.bean.event.EventUploadData;

import java.util.ArrayList;
import java.util.List;

/**
 * 好吧，让你找到了，这是假的数据源
 * <p>
 * Created by sunfusheng on 16/4/22.
 */
public class ModelUtil {

    public static final String type_scenery = "风景";
    public static final String type_building = "建筑";
    public static final String type_animal = "动物";
    public static final String type_plant = "植物";

    public static ModelData getNewModel() {
        ModelData modelData = new ModelData();
        modelData.setName("新建工作模板");
        modelData.setId("");
        List<ModelData.MContentListBean> items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ModelData.MContentListBean item = new ModelData.MContentListBean();
            item.setName("工作流程 " + i);
            items.add(item);
        }
        modelData.setMContentList(items);
        return modelData;
    }

    public static ModelData getModelEmpty() {
        ModelData modelData = new ModelData();
        modelData.setName("空模板");
        modelData.setId("");
        List<ModelData.MContentListBean> items = new ArrayList<>();
        modelData.setMContentList(items);
        return modelData;
    }

    public static EventModelData.DataBean getNewEventModel() {
        EventModelData.DataBean modelData = new EventModelData.DataBean();
        modelData.setName("新建事件模板");
        modelData.setId("");
        List<EventModelData.DataBean.TempletsBean> items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            EventModelData.DataBean.TempletsBean item = new EventModelData.DataBean.TempletsBean();
            item.setName("事件流程 " + i);
            items.add(item);
        }
        modelData.setTemplets(items);
        return modelData;
    }
    public static EventModelData.DataBean getEventModelEmpty() {
        EventModelData.DataBean modelData = new EventModelData.DataBean();
        modelData.setName("空模板");
        modelData.setId("");
        List<EventModelData.DataBean.TempletsBean> items = new ArrayList<>();
        modelData.setTemplets(items);
        return modelData;
    }

    public static ModelData getModel1() {
        ModelData modelData = new ModelData();
        modelData.setName("模板1");
        modelData.setId("");
        List<ModelData.MContentListBean> items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ModelData.MContentListBean item = new ModelData.MContentListBean();
            item.setName("模板1 == " + i);
            items.add(item);
        }
        modelData.setMContentList(items);
        return modelData;
    }

    public static ModelData getModel2() {
        ModelData modelData = new ModelData();
        modelData.setName("模板2");
        modelData.setId("");
        List<ModelData.MContentListBean> items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ModelData.MContentListBean item = new ModelData.MContentListBean();
            item.setName("模板2 ==  " + i);
            items.add(item);
        }
        modelData.setMContentList(items);
        return modelData;
    }

    public static ModelData getModel3() {
        ModelData modelData = new ModelData();
        modelData.setName("模板3");
        modelData.setId("");
        List<ModelData.MContentListBean> items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ModelData.MContentListBean item = new ModelData.MContentListBean();
            item.setName("模板3 ==  " + i);
            items.add(item);
        }
        modelData.setMContentList(items);
        return modelData;
    }

    // 广告数据
    public static List<String> getBannerData() {
        List<String> adList = new ArrayList<>();
        adList.add("http://img2.imgtn.bdimg.com/it/u=2850936076,2080165544&fm=206&gp=0.jpg");
        adList.add("http://img3.imgtn.bdimg.com/it/u=524208507,12616758&fm=206&gp=0.jpg");
        adList.add("http://img3.imgtn.bdimg.com/it/u=698582197,4250615262&fm=206&gp=0.jpg");
        adList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1519377816881&di=47c3c5f8f8e2010ac37d2267736a79a2&imgtype=0&src=http%3A%2F%2Fwww.fansimg.com%2Fportal%2F201406%2F10%2F105328z4xjovysf2kbkyfh.jpg.thumb.jpg");
        return adList;
    }

    // 频道数据
    public static List<ChannelEntity> getChannelData() {
        List<ChannelEntity> channelList = new ArrayList<>();
        channelList.add(new ChannelEntity("中国", "天安门", "http://img2.imgtn.bdimg.com/it/u=2850936076,2080165544&fm=206&gp=0.jpg"));
        channelList.add(new ChannelEntity("美国", "白宫", "http://img3.imgtn.bdimg.com/it/u=524208507,12616758&fm=206&gp=0.jpg"));
        channelList.add(new ChannelEntity("英国", "伦敦塔桥", "http://img3.imgtn.bdimg.com/it/u=698582197,4250615262&fm=206&gp=0.jpg"));
        channelList.add(new ChannelEntity("德国", "城堡", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1519377816881&di=47c3c5f8f8e2010ac37d2267736a79a2&imgtype=0&src=http%3A%2F%2Fwww.fansimg.com%2Fportal%2F201406%2F10%2F105328z4xjovysf2kbkyfh.jpg.thumb.jpg"));
        channelList.add(new ChannelEntity("西班牙", "巴塞罗那", "http://img5.imgtn.bdimg.com/it/u=3191365283,111438732&fm=21&gp=0.jpg"));
        channelList.add(new ChannelEntity("意大利", "比萨斜塔", "http://img5.imgtn.bdimg.com/it/u=482494496,1350922497&fm=206&gp=0.jpg"));
        return channelList;
    }

    // 运营数据
    public static List<OperationEntity> getOperationData() {
        List<OperationEntity> operationList = new ArrayList<>();
        operationList.add(new OperationEntity("度假游", "度假的天堂", "http://img2.imgtn.bdimg.com/it/u=4081165325,36916497&fm=21&gp=0.jpg"));
        operationList.add(new OperationEntity("蜜月游", "浪漫的港湾", "http://img4.imgtn.bdimg.com/it/u=4141168524,78676102&fm=21&gp=0.jpg"));
        return operationList;
    }


    // 分类数据
    public static List<FilterTwoEntity> getCategoryData() {
        List<FilterTwoEntity> list = new ArrayList<>();
        list.add(new FilterTwoEntity(type_scenery, getFilterData()));
        list.add(new FilterTwoEntity(type_building, getFilterData()));
        list.add(new FilterTwoEntity(type_animal, getFilterData()));
        list.add(new FilterTwoEntity(type_plant, getFilterData()));
        return list;
    }

    /**
     * 事件
     * flag
     * 单独事件：1   所有与工作无关的任务、所有人的                                    （副总、总经理初始）
     * 所有事件：0   本人包括管理部门下所有人参与、负责的任务（包括与工作有关以及与工作无关）  (部门经理初始)
     * 参与事件：2   自己参与的所有任务（包括与工作有关以及与工作无关）                    (员工初始)
     * 负责事件：3   自己负责的所有任务（包括与工作有关以及与工作无关）
     */
    public static List<FilterEntity> getZJLEventData() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("单独事件", "1"));
        list.add(new FilterEntity("参与事件", "2"));
        list.add(new FilterEntity("负责事件", "3"));
        list.add(new FilterEntity("所有事件", "4"));
        return list;
    }
    public static List<FilterEntity> getBMJLEventData() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("所有事件", "0"));
        list.add(new FilterEntity("参与事件", "2"));
        list.add(new FilterEntity("负责事件", "3"));
        return list;
    }
    public static List<FilterEntity> getYUANGONGEventData() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("参与事件", "2"));
        list.add(new FilterEntity("负责事件", "3"));
        return list;
    }








    /**
     * 所有工作 0  部门经理有 看所管辖部门下所有人的工作（包括与项目有关、与项目无关）
     * 独立工作 1  副总、总经理有，所有的与项目无关的工作
     * 参与工作 2  自己参与的工作（所有角色都有）
     * 负责工作 3   自己负责的工作（所有角色都有）
     */
    public static List<FilterEntity> getBUJLWorkData() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("所有工作", "0"));
        list.add(new FilterEntity("参与工作", "2"));
        list.add(new FilterEntity("负责工作", "3"));
        return list;
    }

    public static List<FilterEntity> getZJLWorkData() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("单独工作", "1"));
        list.add(new FilterEntity("所有工作", "4"));
        list.add(new FilterEntity("我参与的", "2"));
        list.add(new FilterEntity("我负责的", "3"));
        return list;
    }

    public static List<FilterEntity> getYUANGONGWorkData() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("我参与的", "2"));
        list.add(new FilterEntity("我负责的", "3"));
        return list;
    }


    /**
     * 全部项目，对选择的人名进行项目查询（副总、总经理看全部；部门经理看自己及管辖）
     * 进行中项目，对选择的人名进行项目查询（副总、总经理看全部；部门经理看自己及管辖）
     * 已完结项目，对选择的人名进行项目查询（副总、总经理看全部；部门经理看自己及管辖）
     * 负责项目，只有副总、总经理、部门经理有
     * 参与项目，只有副总、总经理、部门经理有
     */
    public static List<FilterEntity> getProjectAllSortData() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("全部项目", "0"));
        list.add(new FilterEntity("进行中项目", "1"));
        list.add(new FilterEntity("已完结项目", "2"));
        list.add(new FilterEntity("负责项目", "3"));
        list.add(new FilterEntity("参与项目", "4"));
        return list;
    }

    public static List<FilterEntity> getProjectSingleSortData() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("全部项目", "0"));
        list.add(new FilterEntity("进行中项目", "1"));
        list.add(new FilterEntity("已完结项目", "2"));
        return list;
    }


    // 完成
    public static List<FilterEntity> getCompleteData() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("未完成", "0"));
        list.add(new FilterEntity("已完成", "1"));
        return list;
    }

    // 筛选数据
    public static List<FilterEntity> getFilterData() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("中国", "1"));
        list.add(new FilterEntity("美国", "2"));
        list.add(new FilterEntity("英国", "3"));
        list.add(new FilterEntity("德国", "4"));
        list.add(new FilterEntity("西班牙", "5"));
        list.add(new FilterEntity("意大利", "6"));
        return list;
    }

    public static List<EventUploadData> getEventUploadData() {
        List<EventUploadData> list = new ArrayList<>();
        list.add(new EventUploadData("王瑞祥", "", "三分球训练"
                , "完成50个三分球今天训练才可以结束", photos(), texts(), texts(), "2018-06-20 20:40"));
        list.add(new EventUploadData("王瑞祥", "", "三分球训练"
                , "完成50个三分球今天训练才可以结束", photos3(), texts(), texts(), "2018-06-20 20:40"));
        list.add(new EventUploadData("王瑞祥", "", "三分球训练"
                , "完成50个三分球今天训练才可以结束", photos2(), texts(), texts(), "2018-06-20 20:40"));
        list.add(new EventUploadData("王瑞祥", "", "三分球训练"
                , "完成50个三分球今天训练才可以结束", photos(), texts(), texts(), "2018-06-20 20:40"));
        return list;
    }

    public static final List<String> texts() {
        List<String> list = new ArrayList<>();
        list.add("周一训练计划.doc");
        list.add("周二训练计划.txt");
        list.add("周三训练计划.ptf");
        list.add("周四训练计划.ppt");
        list.add("周五训练计划.txt");
        list.add("周六训练计划.xml");
        return list;
    }

    public static final List<Integer> photos() {
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.painting_01);
        list.add(R.drawable.painting_02);
        list.add(R.drawable.painting_03);
        list.add(R.drawable.painting_04);
        list.add(R.drawable.painting_05);
        return list;
    }

    public static final List<Integer> photos3() {
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.painting_02);
        list.add(R.drawable.painting_04);
        list.add(R.drawable.painting_05);
        return list;
    }

    public static final List<Integer> photos2() {
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.painting_01);
        list.add(R.drawable.painting_03);
        return list;
    }


    // 暂无数据
    public static List<TravelingEntity> getNoDataEntity(int height) {
        List<TravelingEntity> list = new ArrayList<>();
        TravelingEntity entity = new TravelingEntity();
        entity.setNoData(true);
        entity.setHeight(height);
        list.add(entity);
        return list;
    }

}
