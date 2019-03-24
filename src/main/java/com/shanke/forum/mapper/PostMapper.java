package com.shanke.forum.mapper;

import com.shanke.forum.entity.PostInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PostMapper {

    @Insert("insert into post(post_id,content,user_id,category_id,create_time,update_time) values(#{postInfo.postId}," +
            "#{postInfo.content},#{userId},#{categoryId},#{postInfo.createTime},#{postInfo.updateTime})")
    void insert(@Param("userId") String userId, @Param("categoryId") String categoryId, @Param("postInfo") PostInfo postInfo);

    @Update("update post set content = #{postInfo.content},update_time = #{postInfo.updateTime},category_id = #{categoryId} where post_id = #{postInfo.postId}")
    void update(@Param("categoryId") String categoryId, @Param("postInfo") PostInfo postInfo);

    @Results({@Result(column = "post_id", property = "postId"),
            @Result(column = "category_id", property = "categoryInfo.categoryId"),
            @Result(column = "title", property = "categoryInfo.title"),
            @Result(column = "update_time", property = "updateTime")})
    @Select("select p.post_id,p.content,c.category_id,c.title,p.update_time from post p join category c on p.category_id = c.category_id where p.post_id = #{postId}")
    PostInfo getOnePost(@Param("postId") String postId, @Param("userId") String userId);

    @Delete("delete from post where post_id = #{postId}")
    void delete(@Param("postId") String postId);

    @Results({@Result(column = "user_id", property = "userInfo.userId"),
            @Result(column = "praise_num", property = "praiseNum"),
            @Result(column = "comment_num", property = "commentNum")})
    @Select("select user_id,praise_num,comment_num from post where post_id = #{postId}")
    PostInfo getByPostId(@Param("postId") String postId);

    @Update("update post set praise_num = #{praiseNum},update_time = #{updateTime} where post_id = #{postId}")
    void updatePraise(@Param("praiseNum") Integer praiseNum, @Param("postId") String postId, @Param("updateTime") String updateTime);

    @Update("update post set comment_num = #{commentNum},update_time = #{updateTime} where post_id = #{postId}")
    void updateComment(@Param("commentNum") Integer commentNum, @Param("postId") String postId, @Param("updateTime") String updateTime);
}
