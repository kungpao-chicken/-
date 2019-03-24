package com.shanke.forum.controller;

import com.shanke.forum.entity.DeviceInfo;
import com.shanke.forum.entity.ResultInfo;
import com.shanke.forum.entity.UserInfo;
import com.shanke.forum.remote.UploadSrv;
import com.shanke.forum.service.UserService;
import com.shanke.forum.validate.Validate;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.util.StringUtils;
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

    @Resource
    private UploadSrv uploadSrv;

    @RequestMapping("/register")
    public ResultInfo register(HttpServletRequest request, UserInfo userInfo, DeviceInfo deviceInfo) {

        MultipartFile file = null;
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request,
                    MultipartHttpServletRequest.class);
            file = multipartRequest.getFile("file");
        }
        log.info("{} access register interface!", userInfo.getNickname());

        ResultInfo resultInfo = Validate.validateRegister(file, userInfo);
        if (resultInfo.getCode() == 1) {
            return resultInfo;
        }

        resultInfo = userService.userIsLegal(userInfo);
        if (resultInfo.getCode() == 1) {
            return resultInfo;
        }

        return userService.register(file, (UserInfo) resultInfo.getData(), deviceInfo);
    }

    @RequestMapping("/login")
    public ResultInfo login(UserInfo userInfo, DeviceInfo deviceInfo) {

        if (StringUtils.isEmpty(userInfo.getAccount())) {
            return new ResultInfo(1, "请填写账号");
        }

        if (StringUtils.isEmpty(userInfo.getPassword())) {
            return new ResultInfo(1, "请填写密码");
        }

        return userService.login(userInfo, deviceInfo);
    }

    @RequestMapping("/update_password")
    public ResultInfo updatePassword(UserInfo userInfo) {
        return userService.updatePassword(userInfo);
    }

    // todo test the interface
    @RequestMapping("/update_info")
    public ResultInfo updateInfo(HttpServletRequest request, UserInfo userInfo) {

        MultipartFile file = null;
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request,
                    MultipartHttpServletRequest.class);
            file = multipartRequest.getFile("file");
        }
        if (file != null) {
            String imgNameNew = uploadSrv.uploadFile(file);
            if (StringUtils.isEmpty(imgNameNew)) {
                return new ResultInfo(1, "系统错误，请重试");
            }

            if ("not-image".equals(imgNameNew)) {
                return new ResultInfo(1, "请判断该上传文件是否为图片");
            }

            userInfo.setAvatar(imgNameNew);
        }

        return userService.updateInfo(userInfo);
    }

    @RequestMapping("/logout")
    public ResultInfo logout(UserInfo userInfo) {
        return userService.logout(userInfo);
    }

}
