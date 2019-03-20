package com.shanke.forum.validate;

import com.shanke.forum.entity.ResultInfo;
import com.shanke.forum.entity.UserInfo;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class Validate {

    public static ResultInfo validateRegister(MultipartFile avatar, UserInfo userInfo) {
        if (avatar == null) {
            return new ResultInfo(1, "请上传头像");
        } else {
            // todo judge is image or not

        }
        if (StringUtils.isEmpty(userInfo.getNickname())) {
            return new ResultInfo(1, "请填写昵称");
        }
        if (StringUtils.isEmpty(userInfo.getPassword())) {
            return new ResultInfo(1, "请填写密码");
        }
        if (StringUtils.isEmpty(userInfo.getPhone())) {
            return new ResultInfo(1, "请填写手机号码");
        }
        if (StringUtils.isEmpty(userInfo.getSex())) {
            return new ResultInfo(1, "请选择性别");
        }
        return new ResultInfo(0, "success");
    }

}
