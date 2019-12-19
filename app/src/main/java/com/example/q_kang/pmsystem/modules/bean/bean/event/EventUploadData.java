package com.example.q_kang.pmsystem.modules.bean.bean.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Q-Kang on 2018/6/20.
 */

public class EventUploadData {

    private String person;
    private String personPhoto;
    private String eventName;
    private String description;
    private List<Integer> photos;
    private List<String> files;
    private List<String> records;
    private String uploadTime;

    public EventUploadData(String person, String personPhoto, String eventName, String description, List<Integer> photos, List<String> files, List<String> records, String uploadTime) {
        this.person = person;
        this.personPhoto = personPhoto;
        this.eventName = eventName;
        this.description = description;
        this.photos = photos;
        this.files = files;
        this.records = records;
        this.uploadTime = uploadTime;
    }

    public List<Integer> getPhotos() {
        if (photos == null) {
            return new ArrayList<>();
        }
        return photos;
    }

    public void setPhotos(List<Integer> photos) {
        this.photos = photos;
    }

    public String getPerson() {
        return person == null ? "" : person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPersonPhoto() {
        return personPhoto == null ? "" : personPhoto;
    }

    public void setPersonPhoto(String personPhoto) {
        this.personPhoto = personPhoto;
    }

    public String getEventName() {
        return eventName == null ? "" : eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getFiles() {
        if (files == null) {
            return new ArrayList<>();
        }
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public List<String> getRecords() {
        if (records == null) {
            return new ArrayList<>();
        }
        return records;
    }

    public void setRecords(List<String> records) {
        this.records = records;
    }

    public String getUploadTime() {
        return uploadTime == null ? "" : uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }
}
