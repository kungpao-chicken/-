package com.shanke.forum.mapper;

import com.shanke.forum.entity.CommentInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment(comment_id,sender_id,content,post_id,create_time,update_time) values(" +
            "#{commentInfo.commentId},#{commentInfo.senderId},#{commentInfo.content},#{commentInfo.postId},#{commentInfo.createTime},#{commentInfo.updateTime})")
    void insert(@Param("commentInfo") CommentInfo commentInfo);

}
