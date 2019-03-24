package com.shanke.forum.service;

import com.shanke.forum.entity.DeviceInfo;
import com.shanke.forum.entity.ResultInfo;
import com.shanke.forum.entity.UserInfo;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    ResultInfo userIsLegal(UserInfo userInfo);

    ResultInfo register(MultipartFile file, UserInfo userInfo, DeviceInfo deviceInfo);

    ResultInfo login(String account, String password, DeviceInfo deviceInfo);

    ResultInfo updatePassword(String userId, String password, String newPassword);

    ResultInfo updateInfo(MultipartFile file, UserInfo userInfo);

    ResultInfo logout(String userId, String token);

    ResultInfo getOneUser(String userId);

    // service call

    void updatePraiseNum(String userId);

    void updateCommentNum(String userId);
}
