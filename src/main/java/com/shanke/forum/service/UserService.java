package com.shanke.forum.service;

import com.shanke.forum.entity.DeviceInfo;
import com.shanke.forum.entity.ResultInfo;
import com.shanke.forum.entity.UserInfo;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    ResultInfo userIsLegal(UserInfo userInfo);

    ResultInfo register(MultipartFile file, UserInfo userInfo, DeviceInfo deviceInfo);

    ResultInfo login(UserInfo userInfo,DeviceInfo deviceInfo);
}
