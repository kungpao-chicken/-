package com.shanke.forum.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserInfo implements Serializable {

    private Integer userId;
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
    private Integer score;
    private String token;
    private String createTime;
    private String updateTime;

    public UserInfo() {
    }

    public UserInfo(String token) {
        this.token = token;
    }

    public UserInfo(String nickname, String sex, String avatar, String birthday, String sign, Integer score, String token) {
        this.sex = sex;
        this.nickname = nickname;
        this.avatar = avatar;
        this.birthday = birthday;
        this.sign = sign;
        this.score = score;
        this.token = token;
    }

}
