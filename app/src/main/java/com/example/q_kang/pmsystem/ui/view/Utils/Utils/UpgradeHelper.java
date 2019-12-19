package com.example.q_kang.pmsystem.ui.view.Utils.Utils;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.q_kang.pmsystem.greendao.gen.DaoMaster;
import com.q_kang.pmsystem.greendao.gen.FileBeanDao;
import com.q_kang.pmsystem.greendao.gen.ImageItemBeanDao;
import com.q_kang.pmsystem.greendao.gen.ScheduleDataDao;
import com.q_kang.pmsystem.greendao.gen.UserBeanDao;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;

public class UpgradeHelper extends DaoMaster.OpenHelper {

    public UpgradeHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Database database = new StandardDatabase(db);
        MigrationHelper.getInstance().migrate(database, ImageItemBeanDao.class, FileBeanDao.class, UserBeanDao.class, ScheduleDataDao.class);
    }
}
