package com.shanke.forum.controller;

import com.shanke.forum.entity.DeviceInfo;
import com.shanke.forum.entity.ResultInfo;
import com.shanke.forum.entity.UserInfo;
import com.shanke.forum.service.UserService;
import com.shanke.forum.validate.Validate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/register")
    public ResultInfo register(@RequestParam("file") MultipartFile file, UserInfo userInfo, DeviceInfo deviceInfo) {

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
}
