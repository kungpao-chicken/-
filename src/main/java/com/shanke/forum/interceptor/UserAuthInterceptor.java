package com.shanke.forum.interceptor;

import com.shanke.forum.entity.DeviceInfo;
import com.shanke.forum.entity.ResultInfo;
import com.shanke.forum.entity.UserInfo;
import com.shanke.forum.mapper.UserMapper;
import com.shanke.forum.remote.RedisSrv;
import com.shanke.forum.utils.JsonUtil;
import com.shanke.forum.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class UserAuthInterceptor implements HandlerInterceptor {

    @Resource
    private RedisSrv redisSrv;

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getParameter("token");
        UserInfo userInfo;
        if (!StringUtils.isEmpty(token)) {
            userInfo = redisSrv.getUserInfo(token);
            if (userInfo != null) {
                request.setAttribute(token, userInfo);
                return true;
            } else {
                userInfo = getUserByToken(token);
                if (userInfo == null) {
                    response2Client(response);
                    return false;
                } else {
                    redisSrv.setUserInfoExpire(token);
                    return true;
                }
            }
        } else {
            DeviceInfo deviceInfo = new DeviceInfo(request.getParameter("IMEI"),
                    request.getParameter("AndroidId"),
                    request.getParameter("mac"),
                    request.getParameter("serialnumber"));
            String newToken = TokenUtil.getToken(deviceInfo);
            userInfo = getUserByToken(newToken);
            if (userInfo == null) {
                response2Client(response);
                return false;
            } else {
                redisSrv.setUserInfoExpire(newToken);
                return true;
            }
        }
    }

    private UserInfo getUserByToken(String newToken) {
        return userMapper.getUserByToken(newToken);
    }

    private void response2Client(HttpServletResponse response) {
        ResultInfo resultInfo = new ResultInfo(1, "need login");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(JsonUtil.object2Json(resultInfo));
        } catch (IOException e) {
            log.error("fail to return client", e);
        }
    }
}
