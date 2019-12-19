package com.example.q_kang.pmsystem.modules.bean.bean;

/**
 * Created by appler on 2018/7/6.
 */

public class ReturnFilePath {


    /**
     * code : 1
     * src : /Upload/2018年07月06日 13时48分18秒/e4bb4e40-bacb-472b-9531-39f0d5b97df2.amr
     * name : e4bb4e40-bacb-472b-9531-39f0d5b97df2
     * msg : 上传成功
     */

    private int code;
    private String src;
    private String name;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    @Override
    public String toString() {
        return "ReturnFilePath{" +
                "code=" + code +
                ", src='" + src + '\'' +
                ", name='" + name + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
