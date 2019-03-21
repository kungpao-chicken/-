package com.shanke.forum.mapper;

import com.shanke.forum.entity.UserInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user(account,username,sex,phone,college,major,nickname,password," +
            "avatar,birthday,sign,login_state,score,token,create_time,update_time) " +
            "values(#{userInfo.account},#{userInfo.username},#{userInfo.sex},#{userInfo.phone}," +
            "#{userInfo.college},#{userInfo.major},#{userInfo.nickname},#{userInfo.password}," +
            "#{userInfo.avatar},#{userInfo.birthday},#{userInfo.sign},#{userInfo.loginState}," +
            "#{userInfo.score},#{userInfo.token},#{userInfo.createTime},#{userInfo.updateTime})")
    void insert(@Param("userInfo") UserInfo userInfo);

    @Results({@Result(column = "user_id", property = "userId")})
    @Select("select user_id,nickname,sex,avatar,birthday,sign,score,token from user where token = #{newToken}")
    UserInfo getUserByToken(@Param("newToken") String newToken);

    @Select("select count(*) from user where account = #{account}")
    Integer getUserByAccount(@Param("account") String nickname);

    @Results({@Result(column = "user_id", property = "userId")})
    @Select("select user_id,nickname,sex,avatar,birthday,sign,score,token from user where account = #{account} and password = #{password}")
    UserInfo getUserByAccountPass(@Param("account") String account, @Param("password") String password);

    @Update("update user set token = #{token} where account = #{account}")
    void updateToken(@Param("account") String account, @Param("token") String token);
}
