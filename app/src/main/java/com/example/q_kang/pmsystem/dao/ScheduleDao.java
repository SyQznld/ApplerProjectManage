package com.example.q_kang.pmsystem.dao;

import com.example.q_kang.pmsystem.base.Application;
import com.example.q_kang.pmsystem.modules.bean.bean.ScheduleData;
import com.q_kang.pmsystem.greendao.gen.ScheduleDataDao;

import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * Created by appler on 2018/8/20.
 */

public class ScheduleDao {

    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param data
     */
    public static void insertLabel(ScheduleData data) {

        Application.getDaoInstant().getScheduleDataDao().insert(data);

    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteLabel(long id) {
        Application.getDaoInstant().getScheduleDataDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param data
     */
    public static void updateLabel(ScheduleData data) {
        Application.getDaoInstant().getScheduleDataDao().update(data);
    }


    /**
     * 查询全部数据
     */
    public static List<ScheduleData> queryAll() {
        return Application.getDaoInstant().getScheduleDataDao().loadAll();
    }

    /**
     * timer
     *
     * @param time
     */
    public static List<ScheduleData> queryByTime(String time) {
        Query query = Application.getDaoInstant().getScheduleDataDao().queryBuilder().where(
                ScheduleDataDao.Properties.StartTime.eq(time))
                .build();
        return query.list();
    }

    public static ScheduleData queryID(long id) {
        Query query = Application.getDaoInstant().getScheduleDataDao().queryBuilder().where(
                ScheduleDataDao.Properties.Id.eq(id))
                .build();
        return (ScheduleData) query.list().get(0);
    }
}
