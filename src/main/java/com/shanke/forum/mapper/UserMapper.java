package com.shanke.forum.mapper;

import com.shanke.forum.entity.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    @Insert("insert into user(nickname,password,avatar,college,major,username,birthday,sex,phone,sign,login_state,grade,score,create_time,update_time) " +
            "values(#{userInfo.nickname},#{userInfo.password},#{userInfo.avatar},#{userInfo.college},#{userInfo.major},#{userInfo.username},#{userInfo.birthday}," +
            "#{userInfo.sex},#{userInfo.phone},#{userInfo.sign},'在线',0,0,'2019-1-1','2019-1-1')")
    void insert(@Param("userInfo") UserInfo userInfo);
}
