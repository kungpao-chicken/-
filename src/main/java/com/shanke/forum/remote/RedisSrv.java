package com.shanke.forum.remote;

import com.shanke.forum.entity.UserInfo;
import com.shanke.forum.utils.JsonUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisSrv {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void saveUserInfo(String key, UserInfo userInfo) {
        stringRedisTemplate.opsForValue().set(key, JsonUtil.object2Json(userInfo));
    }

    public UserInfo getUserInfo(String key) {
        String jsonUserInfo = stringRedisTemplate.opsForValue().get(key);
        return JsonUtil.json2Object(jsonUserInfo, UserInfo.class);
    }

    public void setUserInfoExpire(String key) {
        stringRedisTemplate.expire(key, 7, TimeUnit.DAYS);
    }

}
