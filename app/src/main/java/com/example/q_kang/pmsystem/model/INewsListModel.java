package com.example.q_kang.pmsystem.model;

import com.example.q_kang.pmsystem.modules.bean.bean.NewsData;

/**
 * Created by appler on 2018/7/27.
 */

public interface INewsListModel extends IModel{
    void showNewsList(NewsData newsData);
}
