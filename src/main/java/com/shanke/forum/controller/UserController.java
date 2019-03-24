package com.shanke.forum.controller;

import com.shanke.forum.entity.DeviceInfo;
import com.shanke.forum.entity.ResultInfo;
import com.shanke.forum.entity.UserInfo;
import com.shanke.forum.service.UserService;
import com.shanke.forum.validate.Validate;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public ResultInfo register(HttpServletRequest request, UserInfo userInfo, DeviceInfo deviceInfo) {

        MultipartFile file = getMultipartFile(request);
        log.info("{} access register interface!", userInfo.getNickname());

        ResultInfo resultInfo = Validate.nullValidate(file, userInfo, true);
        if (resultInfo.getCode() == 1) {
            return resultInfo;
        }

        resultInfo = userService.userIsLegal(userInfo);
        if (resultInfo.getCode() == 1) {
            return resultInfo;
        }

        return userService.register(file, (UserInfo) resultInfo.getData(), deviceInfo);
    }

    @PostMapping("/login")
    public ResultInfo login(String account, String password, DeviceInfo deviceInfo) {

        if (StringUtils.isEmpty(account)) {
            return new ResultInfo(1, "请填写账号");
        }

        if (StringUtils.isEmpty(password)) {
            return new ResultInfo(1, "请填写密码");
        }

        return userService.login(account, password, deviceInfo);
    }

    @PostMapping("/new_password")
    public ResultInfo updatePassword(String userId, String password, String newPassword) {
        if (StringUtils.isEmpty(userId)) {
            return new ResultInfo(1, "用户未登录");
        }

        if (StringUtils.isEmpty(password)) {
            return new ResultInfo(1, "请填写旧密码");
        }

        if (StringUtils.isEmpty(newPassword)) {
            return new ResultInfo(1, "请填写新密码");
        }
        return userService.updatePassword(userId, password, newPassword);
    }

    @PostMapping("/update")
    public ResultInfo updateInfo(HttpServletRequest request, UserInfo userInfo) {

        MultipartFile file = getMultipartFile(request);

        ResultInfo resultInfo = Validate.nullValidate(file, userInfo, false);
        if (resultInfo.getCode() == 1) {
            return resultInfo;
        }

        return userService.updateInfo(file, userInfo);
    }

    @GetMapping("/edit")
    public ResultInfo getOneUser(String userId) {
        if (StringUtils.isEmpty(userId)) {
            return new ResultInfo(1, "用户未登录");
        }
        return userService.getOneUser(userId);
    }

    @GetMapping("/logout")
    public ResultInfo logout(String userId, String token) {
        return userService.logout(userId, token);
    }

    @GetMapping("/detail")
    public ResultInfo detail(String userId) {
        return this.getOneUser(userId);
    }

    private MultipartFile getMultipartFile(HttpServletRequest request) {
        MultipartFile file = null;
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request,
                    MultipartHttpServletRequest.class);
            file = multipartRequest.getFile("file");
        }
        return file;
    }

}
