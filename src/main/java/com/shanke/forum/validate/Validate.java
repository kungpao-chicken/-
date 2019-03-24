package com.shanke.forum.validate;

import com.shanke.forum.entity.ResultInfo;
import com.shanke.forum.entity.UserInfo;
import com.shanke.forum.utils.ImgUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class Validate {

    public static ResultInfo validateRegister(MultipartFile avatar, UserInfo userInfo) {

        if (StringUtils.isEmpty(userInfo.getAccount())) {
            return new ResultInfo(1, "请填写账号");
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
        if (avatar == null) {
            return new ResultInfo(1, "请上传头像");
        }
        return new ResultInfo(0);
    }

}
