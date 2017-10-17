package com.duy.finalproject.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by DUY on 8/20/2017.
 */

public class Post {
    @Expose
    @SerializedName("_id")
    private String id;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("content")
    private String content;
    @Expose
    @SerializedName("listImages")
    private ArrayList<String> listImages;
    @Expose
    @SerializedName("date")
    private Date dateOfPost;

    public Post() {
    }

    public Post(String title, String content, ArrayList<String> listImages, Date dateOfPost) {
        this.title = title;
        this.content = content;
        this.listImages = listImages;
        this.dateOfPost = dateOfPost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getListImages() {
        return listImages;
    }

    public void setListImages(ArrayList<String> listImages) {
        this.listImages = listImages;
    }

    public Date getDateOfPost() {
        return dateOfPost;
    }

    public void setDateOfPost(Date dateOfPost) {
        this.dateOfPost = dateOfPost;
    }
}
