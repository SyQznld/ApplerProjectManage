package com.example.q_kang.pmsystem.model;

/**
 * Created by appler on 2018/8/10.
 */

public interface IDocumentModel extends IModel{

    void createDocument(String s);
    void editDocument(String string);

    void getDocumentDetail(String string);

}
