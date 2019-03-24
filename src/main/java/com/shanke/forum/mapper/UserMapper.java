package com.shanke.forum.mapper;

import com.shanke.forum.entity.UserInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user(user_id,account,username,sex,phone,college,major,nickname,password," +
            "avatar,birthday,sign,login_state,score,token,create_time,update_time) " +
            "values(#{userInfo.userId},#{userInfo.account},#{userInfo.username},#{userInfo.sex},#{userInfo.phone}," +
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

    @Update("update user set password = #{password} where user_id = #{userId}")
    void updatePassword(@Param("userId") String userId, @Param("password") String password);

    @Update("update user set nickname = #{userInfo.nickname},avatar = #{userInfo.avatar},sign = #{userInfo.sign}," +
            "birthday = #{userInfo.birthday},phone = #{userInfo.phone} where user_id = #{userInfo.userId}")
    void updateInfo(UserInfo userInfo);

    @Update("update user set token = #{token} where user_id = #{userId}")
    void clearUserToken(@Param("userId") String userId, @Param("token") String token);
}
