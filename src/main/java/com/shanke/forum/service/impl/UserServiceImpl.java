package com.shanke.forum.service.impl;

import com.shanke.forum.entity.DeviceInfo;
import com.shanke.forum.entity.ResultInfo;
import com.shanke.forum.entity.UserInfo;
import com.shanke.forum.mapper.UserMapper;
import com.shanke.forum.remote.QZSrv;
import com.shanke.forum.remote.RedisSrv;
import com.shanke.forum.service.UserService;
import com.shanke.forum.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Value("${shell_path}")
    private String shellPath;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisSrv redisSrv;

    @Resource
    private QZSrv qzSrv;

    @Override
    public ResultInfo userIsLegal(UserInfo userInfo) {
        if (userMapper.getUserByAccount(userInfo.getAccount()) > 0) {
            return new ResultInfo(1, "该账号已经存在，请直接登录");
        }

        return qzSrv.validate(userInfo);
    }

    @Override
    public ResultInfo register(MultipartFile file, UserInfo userInfo, DeviceInfo deviceInfo) {

        String imgNameNew = uploadFile(file);

        if (StringUtils.isEmpty(imgNameNew)) {
            return new ResultInfo(1, "系统错误，上传头像失败");
        } else {
            String avatarUrl = String.format("http://60.205.187.142:9999/forum/%s", imgNameNew);
            log.info("user {} succeed to upload avatar {}", userInfo.getNickname(), avatarUrl);
            userInfo.setAvatar(avatarUrl);

            String token = TokenUtil.getToken(deviceInfo);
            userInfo.setToken(token);
            userInfo.setLoginState("在线");
            userInfo.setScore(0);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            userInfo.setCreateTime(sdf.format(new Date()));
            userInfo.setUpdateTime(sdf.format(new Date()));

            userMapper.insert(userInfo);

            redisSrv.saveUserInfo(token, userInfo);

            return new ResultInfo(0, new UserInfo(userInfo.getNickname(), userInfo.getSex(), userInfo.getAvatar(), userInfo.getBirthday(), userInfo.getSign(), userInfo.getScore(), userInfo.getToken()));
        }
    }

    @Override
    public ResultInfo login(UserInfo userInfo, DeviceInfo deviceInfo) {
        UserInfo loginUser = userMapper.getUserByAccountPass(userInfo.getAccount(), userInfo.getPassword());
        if (loginUser == null) {
            return new ResultInfo(1, "账号不存在，请先注册");
        }

        String token = TokenUtil.getToken(deviceInfo);
        userMapper.updateToken(userInfo.getAccount(), token);
        return new ResultInfo(0, new UserInfo(loginUser.getNickname(), loginUser.getSex(), loginUser.getAvatar(), loginUser.getBirthday(), loginUser.getSign(), loginUser.getScore(), loginUser.getToken()));
    }

    private String uploadFile(MultipartFile file) {
        String imgNameNew = UUID.randomUUID().toString();
        File tmpFile = new File("/data/forum/" + imgNameNew);
        try {
            tmpFile.createNewFile();
        } catch (IOException e) {
            log.error("fail to create file {}", tmpFile, e);
            return null;
        }
        try {
            file.transferTo(tmpFile);
        } catch (IOException e) {
            log.error("fail to upload avatar", e);
        }
        return uploadFileRemote(tmpFile, imgNameNew);
    }

    private String uploadFileRemote(File tmpFile, String imgNameNew) {
        try {
            String[] shPath = new String[]{shellPath, tmpFile.getPath()};
            Process ps = Runtime.getRuntime().exec(shPath);
            ps.waitFor();
        } catch (Exception e) {
            log.error("fail to exec scp.sh", e);
            return null;
        }
        return imgNameNew;
    }
}
