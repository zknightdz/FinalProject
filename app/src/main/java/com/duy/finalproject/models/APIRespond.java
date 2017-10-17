package com.duy.finalproject.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DUY on 7/25/2017.
 */

public class APIRespond<T> {

    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("data")
    @Expose
    private List<T> arr = new ArrayList();

    public APIRespond(int code, List<T> arr) {
        this.code = code;
        this.arr = arr;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<T> getArr() {
        return arr;
    }

    public void setArr(List<T> arr) {
        this.arr = arr;
    }
}
