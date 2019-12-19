package com.example.q_kang.pmsystem.modules.bean.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Frame implements Parcelable{

    /**
     * Id（人员ID）: d3199af0-d63b-4ab9-93d0-5b59423f5c31
     * Name （账户姓名）: SuperAdmin
     * Password （登录密码）: +ZuQcjLq+0Pgflhs9WXAuw==
     * IdCard （身份证）: #
     * Telephone （电话）: 13425640231
     * RealName （真是姓名）: 超级管理员
     * Enabled : true
     * DepartmentId （部门ID）: 00000000-0000-0000-0000-000000000000
     * image （头像）:
     * DepartName （部门名称）:
     * Role （职位）:
     */

    private String Id;
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
    private String IsAndroid;
    private String RoleId;
    private String RegistrationID;


    protected Frame(Parcel in) {
        Id = in.readString();
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
        RoleId = in.readString();
        RegistrationID = in.readString();
    }

    public static final Creator<Frame> CREATOR = new Creator<Frame>() {
        @Override
        public Frame createFromParcel(Parcel in) {
            return new Frame(in);
        }

        @Override
        public Frame[] newArray(int size) {
            return new Frame[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
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
        dest.writeString(RoleId);
        dest.writeString(RegistrationID);
    }

    @Override
    public String toString() {
        return "Frame{" +
                "Id='" + Id + '\'' +
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
                ", IsAndroid='" + IsAndroid + '\'' +
                ", RoleId='" + RoleId + '\'' +
                ", RegistrationID='" + RegistrationID + '\'' +
                '}';
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getIdCard() {
        return IdCard;
    }

    public void setIdCard(String idCard) {
        IdCard = idCard;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public boolean isEnabled() {
        return Enabled;
    }

    public void setEnabled(boolean enabled) {
        Enabled = enabled;
    }

    public String getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(String departmentId) {
        DepartmentId = departmentId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDepartName() {
        return DepartName;
    }

    public void setDepartName(String departName) {
        DepartName = departName;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getIsAndroid() {
        return IsAndroid;
    }

    public void setIsAndroid(String isAndroid) {
        IsAndroid = isAndroid;
    }

    public String getRoleId() {
        return RoleId;
    }

    public void setRoleId(String roleId) {
        RoleId = roleId;
    }

    public String getRegistrationID() {
        return RegistrationID;
    }

    public void setRegistrationID(String registrationID) {
        RegistrationID = registrationID;
    }

    public static Creator<Frame> getCREATOR() {
        return CREATOR;
    }
}
