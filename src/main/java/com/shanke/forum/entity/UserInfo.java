package com.shanke.forum.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfo implements Serializable {

    private String nickname;
    @JsonIgnore
    private String password;
    private String sex;
    private String phone;
    private String avatar;
    private String username;
    private String birthday;
    private String sign;
    private String college;
    private String major;
}
