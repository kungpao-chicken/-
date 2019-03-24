package com.shanke.forum.validate;

import com.shanke.forum.entity.ResultInfo;
import com.shanke.forum.entity.UserInfo;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class Validate {

    public static ResultInfo nullValidate(MultipartFile avatar, UserInfo userInfo, boolean isRegister) {

        if (StringUtils.isEmpty(userInfo.getNickname())) {
            return new ResultInfo(1, "请填写昵称");
        }
        if (isRegister) {
            if (StringUtils.isEmpty(userInfo.getAccount())) {
                return new ResultInfo(1, "请填写账号");
            }

            if (StringUtils.isEmpty(userInfo.getPassword())) {
                return new ResultInfo(1, "请填写密码");
            }
        }
        if (avatar == null) {
            return new ResultInfo(1, "请上传头像");
        }
        return new ResultInfo(0);
    }

}
