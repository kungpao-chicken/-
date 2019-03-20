package com.shanke.forum.entity;

import lombok.Data;

@Data
public class ResultInfo {
    private int code;
    private String msg;
    private Object data;

    public ResultInfo (int code,String msg,Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultInfo (int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ResultInfo setCode(int code) {
        this.code = code;
        return this;
    }

    private ResultInfo setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    private ResultInfo setData(Object data) {
        this.data = data;
        return this;
    }

}
