package com.example.q_kang.pmsystem.modules.bean.bean.project;



public class ProjectAddBean {

    private String key;
    private String value;

    public ProjectAddBean() {
    }

    public ProjectAddBean(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ProjectAddBean{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
