package com.example.q_kang.pmsystem.api;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.adapter.rxjava.Result;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

public interface apiService {

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("Post_Login")
    Observable<ResponseBody> getLoginResult(
            @Field("Name") String Account,
            @Field("Password") String Password,
            @Field("IsAndroid") String IsAndroid,
            @Field("RegistrationID") String registrationID);

    /**
     * 用户
     */
    @GET("Get_Users")
    Observable<ResponseBody> getAllUsers();


    /**
     * 工作模板
     */
    @GET("Get_TemplateList")
    Observable<ResponseBody> getAllModels();


    @FormUrlEncoded
    @POST("Post_Create_MouldName")
    Observable<ResponseBody> loadModel(
            @Field("Id") String id,
            @Field("Name") String name,
            @Field("OldName") String oldname,
            @Field("SortList") Integer[] sortlist,
            @Field("cNameList") String[] cNameList);


    /**
     * 事件模板
     */
    @GET("Get_GetEventTemplet")
    Observable<ResponseBody> getEventModels(@Query("page") int page,
                                            @Query("limit") int limit,
                                            @Query("Templet") String Templet);


    @FormUrlEncoded
    @POST("Post_Create_EventTEmplet")
    Observable<ResponseBody> createEventModel(
            @Field("Id") String id,
            @Field("Name") String name,
            @Field("cNameList") String[] cNameList);


    /**
     * 项目
     */

    @GET("Get_ProListForAndroid")
    //项目列表
    Observable<ResponseBody> getProjectList(@Query("page") int page,
                                            @Query("limit") int limit,
                                            @Query("flag") int flag,
                                            @Query("currUserId") String currUserId,
                                            @Query("keyword") String keyword,
                                            @Query("currRole") String currRole,
                                            @Query("SearchUserId") String SearchUserId,
                                            @Query("Org") String Org,
                                            @Query("Lables") String Lables,
                                            @Query("username") String username);

    @GET("Get_GetProDateil")
        //项目详情
    Observable<ResponseBody> getPro_WorkList(@Query("ProId") String ProId);


    @GET("Get_GetProject")
        //项目下工作
    Observable<ResponseBody> getProDetail(@Query("ProId") String ProId);


    @GET("Get_AddProject")
        //新建项目
    Observable<ResponseBody> createProject(@Query("Json") String Json);


    @GET("Get_EditProject")
        //编辑项目
    Observable<ResponseBody> editProject(@Query("Json") String Json);


    /**
     * 工作
     * 项目工作0   单独工作1   参与工作2   负责工作3
     */
    @GET("Get_GetWorkList")
    Observable<ResponseBody> getWorkList(@Query("page") int page,
                                         @Query("limit") int limit,
                                         @Query("flag") int flag,
                                         @Query("keyword") String keyword,
                                         @Query("userId") String userId,
                                         @Query("SearchUserId") String SearchUserId,
                                         @Query("Org") String Org,
                                         @Query("Lables") String Lables);

    @FormUrlEncoded
    @POST("Post_Create_work")
    Observable<ResponseBody> createWork(@Field("Name") String Name,
                                        @Field("pId") String pId,
                                        @Field("StartTime") String StartTime,
                                        @Field("JoinPersons") String JoinPersons,
                                        @Field("EndTime") String EndTime,
                                        @Field("LeaderId") String LeaderId,
                                        @Field("Content") String Content,
                                        @Field("Location") String Location,
                                        @Field("address") String address,
                                        @Field("AddUserId") String AddUserId,
                                        @Field("Progress") String Progress,
                                        @Field("Lable") String Lable,
                                        @Field("wNameList") String wNameList,
                                        @Field("wLeaderIdList") String wLeaderIdList,
                                        @Field("NextId") String NextId);

    @FormUrlEncoded
    @POST("Post_Edit_work")
    Observable<ResponseBody> editWork(@Field("Id") String Id,
                                      @Field("Name") String Name,
                                      @Field("pId") String pId,
                                      @Field("StartTime") String StartTime,
                                      @Field("JoinPersons") String JoinPersons,
                                      @Field("EndTime") String EndTime,
                                      @Field("LeaderId") String LeaderId,
                                      @Field("Content") String Content,
                                      @Field("Location") String Location,
                                      @Field("address") String address,
                                      @Field("AddUserId") String AddUserId,
                                      @Field("Progress") String Progress,
                                      @Field("Lable") String Lable,
                                      @Field("wNameList") String wNameList,
                                      @Field("wLeaderIdList") String wLeaderIdList,
                                      @Field("NextId") String NextId);


    @GET("Get_GetWorkDateil")
        //工作详情
    Observable<ResponseBody> getWork_EventList(@Query("WorkId") String WorkId);


    @GET("Get_GetWork")
        //工作下事件
    Observable<ResponseBody> getWorkDetail(@Query("WorkId") String WorkId);


    /**
     * 事件
     * flag
     * 单独任务：1   所有与工作无关的任务、所有人的                                  （副总、总经理初始）
     * 所有任务：0   本人包括管理部门下所有人参与、负责的任务（包括与工作有关以及与工作无关）(部门经理初始)
     * 参与任务：2   自己参与的所有任务（包括与工作有关以及与工作无关）                   (员工初始)
     * 负责任务：3   自己负责的所有任务（包括与工作有关以及与工作无关）
     */
    @GET("Get_GetEventList")
    Observable<ResponseBody> getEventList(@Query("page") int page,
                                          @Query("limit") int limit,
                                          @Query("flag") int flag,
                                          @Query("CurrUserId") String CurrUserId,
                                          @Query("keyword") String keyword,
                                          @Query("SearchUserId") String SearchUserId,
                                          @Query("Org") String Org,
                                          @Query("Lables") String Lables);


    @GET("Get_GetEventList")
        //根据条件筛选
    Observable<ResponseBody> getFilterEventList(@Query("page") int page,
                                                @Query("limit") int limit,
                                                @Query("flag") int flag,
                                                @Query("CurrUserId") String CurrUserId,
                                                @Query("keyword") String keyword);


    @Multipart
    @POST("Post_Upload")
    Call<ResponseBody> returnFilePath(@Part MultipartBody.Part file);


    @Multipart
    @POST("Post_Upload")
        //Observable<ResponseBody> getEventPath(@Part MultipartBody.Part file);
    Observable<Result<String>> getEventPath(@PartMap Map<String, RequestBody> file);


    @FormUrlEncoded
    @POST("Post_Create_event")
    Observable<ResponseBody> createEvent(@Field("pId") String pId,
                                         @Field("Title") String Title,
                                         @Field("Content") String Content,
                                         @Field("StartTime") String StartTime,
                                         @Field("EndTime") String EndTime,
                                         @Field("LaunchPerson") String LaunchPerson,
                                         @Field("LeaderId") String LeaderId,
                                         @Field("assignPersons") String AssignPerson,
                                         @Field("Location") String Location,
                                         @Field("address") String address,
                                         @Field("Upload") String Upload,
                                         @Field("Progress") String Progress,
                                         @Field("Lable") String Lable,
                                         @Field("NextId") String NextId);

    @FormUrlEncoded
    @POST("Post_Edit_event")
    Observable<ResponseBody> editEvent(@Field("pId") String pId,
                                       @Field("Id") String Id,
                                       @Field("Title") String Title,
                                       @Field("Content") String Content,
                                       @Field("StartTime") String StartTime,
                                       @Field("EndTime") String EndTime,
                                       @Field("LaunchPerson") String LaunchPerson,
                                       @Field("LeaderId") String LeaderId,
                                       @Field("assignPersons") String AssignPerson,
                                       @Field("Location") String Location,
                                       @Field("address") String address,
                                       @Field("Upload") String Upload,
                                       @Field("Progress") String Progress,
                                       @Field("Lable") String Lable,
                                       @Field("NextId") String NextId);


    //事件完成
    @GET("Get_UpdateFlag_Event")
    Observable<ResponseBody> eventComplete(@Query("guid") String id);


    //事件详情
    @GET("Get_GetEvent")
    Observable<ResponseBody> getEventDetail(@Query("eventId") String eventId);

    //添加事件评论
    @GET("Get_Create_CommentForAndroid")
    Observable<ResponseBody> eventCreateComment(@Query("pId") String pId,
                                                @Query("Content") String Content,
                                                @Query("AdduserId") String AdduserId,
                                                @Query("Id") String Id);

    //获取事件评论
    @GET("Get_CommentForAndroid")
    Observable<ResponseBody> eventGetComment(@Query("pId") String eventId);


    /**
     * 新闻
     */
    @GET("Get_GetNewsList")
    Observable<ResponseBody> getNewsList(@Query("page") int page,
                                         @Query("limit") int limit,
                                         @Query("keyword") String keyword);

    //新闻详情
    @GET("Get_NewsContent")
    Observable<ResponseBody> getNewsDetail(@Query("id") String newsId);


    /***
     *公文
     */
    @FormUrlEncoded
    @POST("Post_Create_Document")
    Observable<ResponseBody> createDocument(@Field("Name") String Name,
                                            @Field("Content") String Content,
                                            @Field("AssignPerson") String AssignPerson,
                                            @Field("Upload") String Upload,
                                            @Field("Pid") String AddUserId,
                                            @Field("LoginUserId") String LoginUserId,
                                            @Field("IsSend") boolean isSend);


    @FormUrlEncoded
    @POST("Post_Edit_Document")
    Observable<ResponseBody> editDocument(@Field("Name") String Name,
                                          @Field("Content") String Content,
                                          @Field("AssignPerson") String AssignPerson,
                                          @Field("Upload") String Upload,
                                          @Field("Pid") String AddUserId,
                                          @Field("LoginUserId") String LoginUserId,
                                          @Field("IsSend") boolean isSend);


    @GET("Get_DocumentList")
        //我发送的
    Observable<ResponseBody> getSendDocuList(@Query("LoginUserId") String LoginUserId,
                                             @Query("IsSend") boolean IsSend);


    @GET("Get_DocumentListReceive")
        //我接收的
    Observable<ResponseBody> getReceiveDocList(@Query("LoginUserId") String LoginUserId);

    @GET("Get_Update_Send_Receive")
        //更新公文已读未读状态
    Observable<ResponseBody> updateReceiveDoc(@Query("Id") String Id,
                                              @Query("IsRead") boolean IsRead);


    //根据公文id得到详情
    @GET("DocumentDetail")
    Observable<ResponseBody> getDocumentDetail(@Query("Id") String docId);


    /**
     * 全局搜索
     */
    @GET("GetALLSearch")
    Observable<ResponseBody> getGlobalSearch(@Query("flag") int flag,
                                             @Query("keyword") String keyword,
                                             @Query("state") String state,
                                             @Query("UserId") String UserId);


    //个人关联项目、工作、事件数据
    @GET("Get_GetAllByUserId")
    Observable<ResponseBody> getDataById(@Query("userId") String userId);

    //根据用户角色返回科室
    @GET("Get_Organization")
    Observable<ResponseBody> getDepartment(@Query("LoginUserId") String currUserId);


    //根据用户角色返回管辖人员
    @GET("Get_UserByUserAndRole")
    Observable<ResponseBody> getSearchUserById(@Query("currUserId") String currUserId,
                                               @Query("currRole") String currRole);


    //删除 项目、工作、事件条目
    @GET("Get_Delete_Thing")
    Observable<ResponseBody> deleteListItem(@Query("id") String id,
                                            @Query("type") String type);


    /**
     * 日程
     */
    //新建日程
    @FormUrlEncoded
    @POST("Post_Create_Schedule")
    Observable<ResponseBody> createSchedule(@Field("title") String title,
                                            @Field("content") String content,
                                            @Field("startTime") String startTime,
                                            @Field("endTime") String endTime,
                                            @Field("isFinish") boolean isFinish,
                                            @Field("ItemId") String ItemId,
                                            @Field("ItemName") String ItemName,
                                            @Field("ItemType") int ItemType,
                                            @Field("IsDelete") boolean IsDelete,
                                            @Field("IsAllDay") boolean IsAllDay,
                                            @Field("AddTime") String AddTime);


    //编辑日程
    @FormUrlEncoded
    @POST("Post_Update_Schedule")
    Observable<ResponseBody> editSchedule(@Field("Id") String Id,
                                          @Field("title") String title,
                                          @Field("content") String content,
                                          @Field("startTime") String startTime,
                                          @Field("endTime") String endTime,
                                          @Field("isFinish") boolean isFinish,
                                          @Field("ItemId") String ItemId,
                                          @Field("ItemName") String ItemName,
                                          @Field("ItemType") int ItemType,
                                          @Field("IsDelete") boolean IsDelete,
                                          @Field("IsAllDay") boolean IsAllDay,
                                          @Field("AddTime") String AddTime
    );

    //日程列表
    @GET("Get_ScheduleList")
    Observable<ResponseBody> getScheduleList(@Query("DateTime") String DateTime,
                                             @Query("Flag") int flag);


    //某一天日程列表
    @GET("Get_ScheduleDaysList")
    Observable<ResponseBody> getDayScheduleList(@Query("DateTime") String DateTime);


    //获取某一条日程详情
    @GET("Get_Schedule")
    Observable<ResponseBody> getScheduleDetail(@Query("guid") String guid);


    /**
     * 消息通知
     */
    //消息通知 列表
    @GET("Get_MessagesList")
    Observable<ResponseBody> getMessageList(@Query("LoginUserId") String id);


    //消息 更新已读未读状态
    @GET("Get_UpdateMessage")
    Observable<ResponseBody> updateMessage(@Query("Message_Id") String Message_Id);



    //消息 删除 单条或全部    type 2 单个删除  1 删除全部
    @GET("Get_MsgDelete")
    Observable<ResponseBody> deleteMessage(@Query("Id") String userId,@Query("type") String type);







    /**
     * 版本更新
     */
    @GET("Get_VersionChange")
    Observable<ResponseBody> versionUpdate(@Query("Version") String Version);


    /**
     * 重置密码
     */
    @GET("EditPwd")
    Observable<ResponseBody> resetPassword(@Query("oldpwd") String oldpwd,
                                           @Query("newpwd") String newpwd,
                                           @Query("tnewpwd") String tnewpwd,
                                           @Query("Id") String Id);


}
