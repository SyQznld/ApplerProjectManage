package com.example.q_kang.pmsystem.dao;

import com.example.q_kang.pmsystem.base.Application;
import com.example.q_kang.pmsystem.modules.bean.bean.UserBean;

import java.util.List;

/**
 * Created by appler on 2018/7/6.
 */

public class AdminDao {

    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param data
     */
    public static void insertLabel(UserBean data) {
        Application.getDaoInstant().getUserBeanDao().insert(data);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteLabel(long id) {
        Application.getDaoInstant().getUserBeanDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param data
     */
    public static void updateLabel(UserBean data) {
        Application.getDaoInstant().getUserBeanDao().update(data);
    }


    /**
     * 查询全部数据
     */
    public static List<UserBean> queryAll() {
        return Application.getDaoInstant().getUserBeanDao().loadAll();
    }


    public static String getUserID() {
        List<UserBean> accountTables = Application.getDaoInstant().getUserBeanDao().loadAll();
        if (accountTables.size() > 0) {
            UserBean adminData = accountTables.get(0);
            return adminData.getUserId();
        }
        return null;

    }

    public static UserBean getUser() {
        List<UserBean> accountTables = Application.getDaoInstant().getUserBeanDao().loadAll();
        if (accountTables.size() > 0) {
            UserBean adminData = accountTables.get(0);
            return adminData;
        }
        return null;

    }


    /**
     * 删除全部数据
     */
    public static void deleAllData() {
        Application.getDaoInstant().getUserBeanDao().deleteAll();
    }

}
