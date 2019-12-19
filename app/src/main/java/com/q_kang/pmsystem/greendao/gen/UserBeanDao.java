package com.q_kang.pmsystem.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.q_kang.pmsystem.modules.bean.bean.UserBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_BEAN".
*/
public class UserBeanDao extends AbstractDao<UserBean, Long> {

    public static final String TABLENAME = "USER_BEAN";

    /**
     * Properties of entity UserBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserId = new Property(1, String.class, "userId", false, "USER_ID");
        public final static Property Name = new Property(2, String.class, "Name", false, "NAME");
        public final static Property Password = new Property(3, String.class, "Password", false, "PASSWORD");
        public final static Property IdCard = new Property(4, String.class, "IdCard", false, "ID_CARD");
        public final static Property Telephone = new Property(5, String.class, "Telephone", false, "TELEPHONE");
        public final static Property RealName = new Property(6, String.class, "RealName", false, "REAL_NAME");
        public final static Property Enabled = new Property(7, boolean.class, "Enabled", false, "ENABLED");
        public final static Property DepartmentId = new Property(8, String.class, "DepartmentId", false, "DEPARTMENT_ID");
        public final static Property Image = new Property(9, String.class, "image", false, "IMAGE");
        public final static Property DepartName = new Property(10, String.class, "DepartName", false, "DEPART_NAME");
        public final static Property Role = new Property(11, String.class, "Role", false, "ROLE");
        public final static Property RoleId = new Property(12, String.class, "RoleId", false, "ROLE_ID");
        public final static Property IsAndroid = new Property(13, String.class, "IsAndroid", false, "IS_ANDROID");
    }


    public UserBeanDao(DaoConfig config) {
        super(config);
    }
    
    public UserBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"USER_ID\" TEXT," + // 1: userId
                "\"NAME\" TEXT," + // 2: Name
                "\"PASSWORD\" TEXT," + // 3: Password
                "\"ID_CARD\" TEXT," + // 4: IdCard
                "\"TELEPHONE\" TEXT," + // 5: Telephone
                "\"REAL_NAME\" TEXT," + // 6: RealName
                "\"ENABLED\" INTEGER NOT NULL ," + // 7: Enabled
                "\"DEPARTMENT_ID\" TEXT," + // 8: DepartmentId
                "\"IMAGE\" TEXT," + // 9: image
                "\"DEPART_NAME\" TEXT," + // 10: DepartName
                "\"ROLE\" TEXT," + // 11: Role
                "\"ROLE_ID\" TEXT," + // 12: RoleId
                "\"IS_ANDROID\" TEXT);"); // 13: IsAndroid
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(2, userId);
        }
 
        String Name = entity.getName();
        if (Name != null) {
            stmt.bindString(3, Name);
        }
 
        String Password = entity.getPassword();
        if (Password != null) {
            stmt.bindString(4, Password);
        }
 
        String IdCard = entity.getIdCard();
        if (IdCard != null) {
            stmt.bindString(5, IdCard);
        }
 
        String Telephone = entity.getTelephone();
        if (Telephone != null) {
            stmt.bindString(6, Telephone);
        }
 
        String RealName = entity.getRealName();
        if (RealName != null) {
            stmt.bindString(7, RealName);
        }
        stmt.bindLong(8, entity.getEnabled() ? 1L: 0L);
 
        String DepartmentId = entity.getDepartmentId();
        if (DepartmentId != null) {
            stmt.bindString(9, DepartmentId);
        }
 
        String image = entity.getImage();
        if (image != null) {
            stmt.bindString(10, image);
        }
 
        String DepartName = entity.getDepartName();
        if (DepartName != null) {
            stmt.bindString(11, DepartName);
        }
 
        String Role = entity.getRole();
        if (Role != null) {
            stmt.bindString(12, Role);
        }
 
        String RoleId = entity.getRoleId();
        if (RoleId != null) {
            stmt.bindString(13, RoleId);
        }
 
        String IsAndroid = entity.getIsAndroid();
        if (IsAndroid != null) {
            stmt.bindString(14, IsAndroid);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(2, userId);
        }
 
        String Name = entity.getName();
        if (Name != null) {
            stmt.bindString(3, Name);
        }
 
        String Password = entity.getPassword();
        if (Password != null) {
            stmt.bindString(4, Password);
        }
 
        String IdCard = entity.getIdCard();
        if (IdCard != null) {
            stmt.bindString(5, IdCard);
        }
 
        String Telephone = entity.getTelephone();
        if (Telephone != null) {
            stmt.bindString(6, Telephone);
        }
 
        String RealName = entity.getRealName();
        if (RealName != null) {
            stmt.bindString(7, RealName);
        }
        stmt.bindLong(8, entity.getEnabled() ? 1L: 0L);
 
        String DepartmentId = entity.getDepartmentId();
        if (DepartmentId != null) {
            stmt.bindString(9, DepartmentId);
        }
 
        String image = entity.getImage();
        if (image != null) {
            stmt.bindString(10, image);
        }
 
        String DepartName = entity.getDepartName();
        if (DepartName != null) {
            stmt.bindString(11, DepartName);
        }
 
        String Role = entity.getRole();
        if (Role != null) {
            stmt.bindString(12, Role);
        }
 
        String RoleId = entity.getRoleId();
        if (RoleId != null) {
            stmt.bindString(13, RoleId);
        }
 
        String IsAndroid = entity.getIsAndroid();
        if (IsAndroid != null) {
            stmt.bindString(14, IsAndroid);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public UserBean readEntity(Cursor cursor, int offset) {
        UserBean entity = new UserBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // Name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // Password
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // IdCard
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // Telephone
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // RealName
            cursor.getShort(offset + 7) != 0, // Enabled
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // DepartmentId
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // image
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // DepartName
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // Role
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // RoleId
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13) // IsAndroid
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPassword(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setIdCard(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTelephone(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setRealName(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setEnabled(cursor.getShort(offset + 7) != 0);
        entity.setDepartmentId(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setImage(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setDepartName(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setRole(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setRoleId(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setIsAndroid(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UserBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UserBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
