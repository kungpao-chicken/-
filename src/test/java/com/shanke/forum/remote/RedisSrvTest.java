package com.shanke.forum.remote;

import com.shanke.forum.BaseTest;
import com.shanke.forum.entity.UserInfo;
import org.junit.Test;

import javax.annotation.Resource;

public class RedisSrvTest extends BaseTest {

    @Resource
    private RedisSrv redisSrv;

    @Test
    public void saveUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setNickname("wuchaojing");
        redisSrv.saveUserInfo("b", userInfo);
    }

    @Test
    public void getUserInfo() {
        System.out.println(redisSrv.getUserInfo("b"));
    }

}
