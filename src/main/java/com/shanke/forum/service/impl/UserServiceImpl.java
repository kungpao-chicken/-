package com.shanke.forum.service.impl;

import com.shanke.forum.entity.ResultInfo;
import com.shanke.forum.entity.UserInfo;
import com.shanke.forum.mapper.UserMapper;
import com.shanke.forum.remote.RedisSrv;
import com.shanke.forum.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Value("${shellPath}")
    private String shellPath;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisSrv redisSrv;

    @Transactional
    @Override
    public ResultInfo register(MultipartFile file, UserInfo userInfo) {
        String imgNameNew = uploadFile(file);
        if (StringUtils.isEmpty(imgNameNew)) {
            return new ResultInfo(1, "系统错误，上传头像失败");
        } else {
            String avatarUrl = String.format("http://60.205.187.142:9999/forum/%s", imgNameNew);
            log.info("user {} succeed to upload avatar {}", userInfo.getNickname(), avatarUrl);
            userInfo.setAvatar(avatarUrl);
            userMapper.insert(userInfo);
            String key = UUID.randomUUID().toString();
            redisSrv.saveUserInfo(key, userInfo);
            return new ResultInfo(0, "success", userInfo);
        }
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
