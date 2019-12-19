package com.example.q_kang.pmsystem.dao;


import com.example.q_kang.pmsystem.base.Application;
import com.example.q_kang.pmsystem.modules.bean.bean.FileBean;
import com.q_kang.pmsystem.greendao.gen.FileBeanDao;

import org.greenrobot.greendao.query.Query;

import java.util.List;

public class FileDao {

    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param data
     */
    public static void insertLabel(FileBean data) {

        Application.getDaoInstant().getFileBeanDao().insert(data);

    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteLabel(long id) {
        Application.getDaoInstant().getFileBeanDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param data
     */
    public static void updateLabel(FileBean data) {
        Application.getDaoInstant().getFileBeanDao().update(data);
    }


    /**
     * 查询全部数据
     */
    public static List<FileBean> queryAll() {
        return  Application.getDaoInstant().getFileBeanDao().loadAll();
    }

    /**
     * timer
     *
     * @param time
     */
    public static List<FileBean> queryByChooseTime(String time) {
        Query query =  Application.getDaoInstant().getFileBeanDao().queryBuilder().where(
                FileBeanDao.Properties.ChooseTime.eq(time))
                .build();
        return query.list();
    }

    public static FileBean queryID(long id) {
        Query query =  Application.getDaoInstant().getFileBeanDao().queryBuilder().where(
                FileBeanDao.Properties.Id.eq(id))
                .build();
        return (FileBean) query.list().get(0);
    }
}
