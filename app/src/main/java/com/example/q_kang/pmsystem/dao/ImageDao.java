package com.example.q_kang.pmsystem.dao;


import com.example.q_kang.pmsystem.base.Application;
import com.example.q_kang.pmsystem.modules.bean.bean.photo.ImageItemBean;
import com.q_kang.pmsystem.greendao.gen.ImageItemBeanDao;

import org.greenrobot.greendao.query.Query;

import java.util.List;


public class ImageDao {
    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param data
     */
    public static void insertLabel(ImageItemBean data) {


        Application.getDaoInstant().getImageItemBeanDao().insert(data);

    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteLabel(long id) {
        Application.getDaoInstant().getImageItemBeanDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param data
     */
    public static void updateLabel(ImageItemBean data) {
        Application.getDaoInstant().getImageItemBeanDao().update(data);
    }


    /**
     * 查询全部数据
     */
    public static List<ImageItemBean> queryAll() {
        return Application.getDaoInstant().getImageItemBeanDao().loadAll();
    }

    /**
     * timer
     *
     * @param time
     */
    public static List<ImageItemBean> queryTimer(String time) {
        Query query = Application.getDaoInstant().getImageItemBeanDao().queryBuilder().where(
                ImageItemBeanDao.Properties.Time.eq(time))
                .build();
        return query.list();
    }

    public static ImageItemBean queryID(long id) {
        Query query = Application.getDaoInstant().getImageItemBeanDao().queryBuilder().where(
                ImageItemBeanDao.Properties.Id.eq(id))
                .build();
        return (ImageItemBean) query.list().get(0);
    }
}
