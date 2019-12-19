package com.example.q_kang.pmsystem.Globle;


public class Globle {

//    public static final String PHOTO_URL = "http://192.168.2.175:8082";      //  SSH
    //zjl 111 总经理    dongqitian 111 部门经理    xiaohong 111 员工


//    public static final String PHOTO_URL = "http://119.29.231.80:8188";     //公司服务器
    public static final String PHOTO_URL = "http://www.apekj.cn:8888";         //发布后的产品Ip
    public static final String URL = Globle.PHOTO_URL + "/api/Default/";
    public static final String NEWS_CONTENT_URL = Globle.PHOTO_URL + "/NewsContent.html?id=";


    public static final int LOADING_STATE = 1001;//加载中
    public static final int LOADING_FAIL = 1002;//加载失败
    public static final int LOADING_SUCEESS = 1003;//加载成功
    public static final int UPLOADING_STATE = 2001;//上传中
    public static final int UPLOADING_FAIL = 2002;//上传失败
    public static final int UPLOADING_SUCCESS = 2003;//上传成功

    /**
     * 工作模板
     */
    public static final int MODEL_CHECK = 1200;//查看模板，可编辑
    public static final int MODEL_EDIT = 1201;//新建模板
    public static final int MODEL_SHOW = 1202;//查看模板，不可编辑（固定模板123）
    public static final int MODEL_UPLOAD = 1203;//上传模板（上传带模板ID为修改模板，无ID为新建模板）

    /**
     * 事件模板
     */
    public static final int EVENT_MODEL_CHECK = 1300;//查看模板，可编辑
    public static final int EVENT_MODEL_EDIT = 1301;//新建模板
    public static final int EVENT_MODEL_SHOW = 1302;//查看模板，不可编辑（固定模板123）
    public static final int EVENT_MODEL_UPLOAD = 1303;//上传事件模板（上传带模板ID为修改模板，无ID为新建模板）


    public static final int EVENT_EDIT = 1205;//事件编辑
    public static final int WORK_EDIT = 1206;//工作编辑
    public static final int PROJECT_EDIT = 1207;//项目编辑
    public static final int DOCUMENT_EDIT = 1208;//项目编辑

    public static final int PAGE_LIMIT = 35;//分页加载每页条数

    /**
     * 角色
     */
    public static final String ROLE_FZ = "副总经理";
    public static final String ROLE_ZJL = "总经理";
    public static final String ROLE_BMJL = "部门经理";
    public static final String ROLE_YUANGONG = "员工";

    public static final String ROLE_FJZ = "副局长";
    public static final String ROLE_JZ = "局长";
    public static final String ROLE_KZ = "科长";
    public static final String ROLE_KY = "科员";

    public static final String ROLE_DUCHA = "督察";


}
