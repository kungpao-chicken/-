package com.shanke.forum.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResultInfo {
    private int code;
    private String msg;
    private Object data;

    public ResultInfo(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public ResultInfo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultInfo(int code) {
        this.code = code;
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
