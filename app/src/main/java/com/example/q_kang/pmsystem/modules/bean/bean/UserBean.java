package com.example.q_kang.pmsystem.modules.bean.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserBean implements Parcelable{


    /**
     * Id : 9cbe899d-13ad-4d76-8568-a967151bcf15
     * Name : dnn
     * Password : +ZuQcjLq+0Pgflhs9WXAuw==
     * IdCard : a
     * Telephone : 12345678910
     * RealName : 段妞妞
     * Enabled : true
     * DepartmentId : df8c05be-acfe-4222-bf9d-4b6f21826e22
     * image : /Upload/2018年06月25日 15时46分24秒/3.jpg
     * DepartName : 软件一部
     * Role : 部门经理
     * RoleId : 部门id
     * IsAndroid : null
     */

    @Id
    private Long id;

    private String userId;
    private String Name;
    private String Password;
    private String IdCard;
    private String Telephone;
    private String RealName;
    private boolean Enabled;
    private String DepartmentId;
    private String image;
    private String DepartName;
    private String Role;
    private String RoleId;
    private String IsAndroid;




    @Generated(hash = 1605451754)
    public UserBean(Long id, String userId, String Name, String Password, String IdCard,
                    String Telephone, String RealName, boolean Enabled, String DepartmentId,
                    String image, String DepartName, String Role, String RoleId, String IsAndroid) {
        this.id = id;
        this.userId = userId;
        this.Name = Name;
        this.Password = Password;
        this.IdCard = IdCard;
        this.Telephone = Telephone;
        this.RealName = RealName;
        this.Enabled = Enabled;
        this.DepartmentId = DepartmentId;
        this.image = image;
        this.DepartName = DepartName;
        this.Role = Role;
        this.RoleId = RoleId;
        this.IsAndroid = IsAndroid;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }

    protected UserBean(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        userId = in.readString();
        Name = in.readString();
        Password = in.readString();
        IdCard = in.readString();
        Telephone = in.readString();
        RealName = in.readString();
        Enabled = in.readByte() != 0;
        DepartmentId = in.readString();
        image = in.readString();
        DepartName = in.readString();
        Role = in.readString();
        IsAndroid = in.readString();
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel in) {
            return new UserBean(in);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPassword() {
        return this.Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getIdCard() {
        return this.IdCard;
    }

    public void setIdCard(String IdCard) {
        this.IdCard = IdCard;
    }

    public String getTelephone() {
        return this.Telephone;
    }

    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }

    public String getRealName() {
        return this.RealName;
    }

    public void setRealName(String RealName) {
        this.RealName = RealName;
    }

    public boolean getEnabled() {
        return this.Enabled;
    }

    public void setEnabled(boolean Enabled) {
        this.Enabled = Enabled;
    }

    public String getDepartmentId() {
        return this.DepartmentId;
    }

    public void setDepartmentId(String DepartmentId) {
        this.DepartmentId = DepartmentId;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDepartName() {
        return this.DepartName;
    }

    public void setDepartName(String DepartName) {
        this.DepartName = DepartName;
    }

    public String getRole() {
        return this.Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }

    public String getIsAndroid() {
        return this.IsAndroid;
    }

    public void setIsAndroid(String IsAndroid) {
        this.IsAndroid = IsAndroid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(userId);
        dest.writeString(Name);
        dest.writeString(Password);
        dest.writeString(IdCard);
        dest.writeString(Telephone);
        dest.writeString(RealName);
        dest.writeByte((byte) (Enabled ? 1 : 0));
        dest.writeString(DepartmentId);
        dest.writeString(image);
        dest.writeString(DepartName);
        dest.writeString(Role);
        dest.writeString(IsAndroid);
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", Name='" + Name + '\'' +
                ", Password='" + Password + '\'' +
                ", IdCard='" + IdCard + '\'' +
                ", Telephone='" + Telephone + '\'' +
                ", RealName='" + RealName + '\'' +
                ", Enabled=" + Enabled +
                ", DepartmentId='" + DepartmentId + '\'' +
                ", image='" + image + '\'' +
                ", DepartName='" + DepartName + '\'' +
                ", Role='" + Role + '\'' +
                ", RoleId='" + RoleId + '\'' +
                ", IsAndroid='" + IsAndroid + '\'' +
                '}';
    }

    public String getRoleId() {
        return this.RoleId;
    }

    public void setRoleId(String RoleId) {
        this.RoleId = RoleId;
    }
}
