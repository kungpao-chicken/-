package com.shanke.forum.controller;

import com.shanke.forum.entity.ResultInfo;
import com.shanke.forum.entity.UserInfo;
import com.shanke.forum.service.UserService;
import com.shanke.forum.validate.Validate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/register")
    public ResultInfo register(@RequestParam("file") MultipartFile file, UserInfo userInfo) {
        ResultInfo resultInfo = Validate.validateRegister(file, userInfo);

        if (resultInfo.getCode() == 1) {
            return resultInfo;
        }
        return userService.register(file, userInfo);


    }


}
