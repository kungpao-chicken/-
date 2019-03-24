package com.shanke.forum.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserInfo implements Serializable {

    private String userId;
    private String account; //学号，唯一
    private String username;
    private String sex;
    private String phone;
    private String college;
    private String major;

    //----- above is from qiangzhi，below is from selfDefine--------

    private String nickname;
    @JsonIgnore
    private String password;
    private String avatar;
    private String birthday;
    private String sign;


    private String loginState;
    private int score;
    private int praiseNum;
    private int commentNum;
    private String token;
    private String createTime;
    private String updateTime;

    public UserInfo() {
    }

    public UserInfo(String token) {
        this.token = token;
    }

    public UserInfo(String userId, String nickname, String sex, String avatar, String birthday, String sign, Integer score, String token) {
        this.userId = userId;
        this.sex = sex;
        this.nickname = nickname;
        this.avatar = avatar;
        this.birthday = birthday;
        this.sign = sign;
        this.score = score;
        this.token = token;
    }

}
