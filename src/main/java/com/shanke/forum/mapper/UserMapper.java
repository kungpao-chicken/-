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

    @Results({@Result(column = "user_id", property = "userId"),
            @Result(column = "praise_num", property = "praiseNum"),
            @Result(column = "comment_num", property = "commentNum")})
    @Select("select user_id,nickname,sex,avatar,birthday,sign,score,praise_num,comment_num,token from user where token = #{newToken}")
    UserInfo getUserByToken(@Param("newToken") String newToken);

    @Select("select count(*) from user where account = #{account}")
    Integer getUserByAccount(@Param("account") String account);

    @Results({@Result(column = "user_id", property = "userId"),
            @Result(column = "praise_num", property = "praiseNum"),
            @Result(column = "comment_num", property = "commentNum")})
    @Select("select user_id,nickname,sex,avatar,birthday,sign,score,praise_num,comment_num,token from user where account = #{account} and password = #{password}")
    UserInfo getUserByAccountPass(@Param("account") String account, @Param("password") String password);

    @Update("update user set token = #{token},update_time = #{updateTime} where account = #{account}")
    void updateToken(@Param("account") String account, @Param("token") String token, @Param("updateTime") String updateTime);

    @Update("update user set password = #{password},update_time = #{updateTime} where user_id = #{userId}")
    void updatePassword(@Param("userId") String userId, @Param("password") String password, @Param("updateTime") String updateTime);

    @Update("update user set nickname = #{userInfo.nickname},avatar = #{userInfo.avatar},sign = #{userInfo.sign}," +
            "birthday = #{userInfo.birthday},update_time = #{userInfo.updateTime} where user_id = #{userInfo.userId}")
    void updateInfo(@Param("userInfo") UserInfo userInfo);

    @Update("update user set token = #{token},update_time = #{updateTime} where user_id = #{userId}")
    void clearUserToken(@Param("userId") String userId, @Param("token") String token, @Param("updateTime") String updateTime);

    @Select("select nickname,avatar,birthday,sign from user where user_id = #{userId}")
    UserInfo getOneUser(@Param("userId") String userId);

    @Select("select count(*) from user where user_id = #{userId} and password = #{password}")
    int getUserByUserIdPass(@Param("userId") String userId, @Param("password") String password);

    @Select("select praise_num from user where user_id = #{userId}")
    int getPraiseNum(@Param("userId") String userId);

    @Update("update user set praise_num = #{praiseNum} where user_id = #{userId}")
    void updatePraiseNum(@Param("userId") String userId, @Param("praiseNum") int praiseNum);

    @Select("select comment_num from user where user_id = #{userId}")
    int getCommentNum(String userId);

    @Update("update user set comment_num = #{commentNum} where user_id = #{userId}")
    void updateCommentNum(@Param("userId") String userId, @Param("commentNum") int commentNum);
}
